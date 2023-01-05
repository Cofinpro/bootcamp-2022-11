import {defineStore} from 'pinia'
import type {LoginRequest} from "@/models/LoginRequest";
import router from "@/router";
import axiosInstance from "@/axios";

export const useAuthStore = defineStore('auth', {
    state: () =>({
        loggedIn: false,
        username: "",
        role: "",
    }),

    actions: {
        login(user: LoginRequest): void{
            axiosInstance.post("/api/v1/token", user).then((result) => {
                localStorage.setItem("access_token", result.data.tokens["access_token"]);
                localStorage.setItem("refresh_token", result.data.tokens["refresh_token"]);
                localStorage.setItem("username", result.data.username);
                localStorage.setItem("role", result.data.role.authority);
                this.loggedIn = true;
                this.username = result.data.username;
                this.role = result.data.role.authority;
                router.push('/');
            }).catch((error) => {
                console.log(error.response)
            });
        },
        logout(): void{
           localStorage.clear();
           this.loggedIn = false;
           this.username = "";
           this.role = "";
           router.push("/login");
        },
        async isLoggedIn(): Promise<boolean> {
            const refreshToken = localStorage.getItem("refresh_token");
            const username = localStorage.getItem("username");

            const user = {
                "refreshToken": refreshToken,
                "username": username,
            }

            return await axiosInstance.post("/api/v1/token/verify", user)
                .then((result) => {
                    if (result.data) {
                        this.loggedIn = true;
                        return true;
                    }
                    this.loggedIn = false;
                    return false;
                }).catch((error) => {
                    console.log(error.response)
                    this.loggedIn = false;
                    return false;
                })
        },
        isAdmin(): boolean{
            return localStorage.getItem('role') === 'ROLE_ADMIN'
        }
    }

})
