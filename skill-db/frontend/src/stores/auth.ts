import {ref, computed} from 'vue'
import {defineStore} from 'pinia'
import axios from "axios";
import type {LoginRequest} from "@/models/LoginRequest";
import router from "@/router";

export const useAuthStore = defineStore('auth', {
    state: () =>({
        loggedIn: false,
        username: "",
        role: "",
    }),

    actions: {
        login(user: LoginRequest): void{
            axios.post("/api/v1/token", user).then((result) => {
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
    }

})
