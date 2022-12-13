import {ref, computed} from 'vue'
import {defineStore} from 'pinia'
import axios from "axios";
import type {LoginRequest} from "@/model/LoginRequest";
import router from "@/router";

export const useAuthStore = defineStore('auth', {
    state: () =>({
        loggedIn: false,
    }),

    actions: {
        login(user: LoginRequest): void{
            axios.post("/api/v1/token", user).then((result) => {
                localStorage.setItem("access_token", result.data.tokens["access_token"]);
                localStorage.setItem("refresh_token", result.data.tokens["refresh_token"]);
                localStorage.setItem("username", result.data.username);
                this.loggedIn = true;
                router.push('/');
            }).catch((error) => {
                console.log(error.response)
            })
        },
        logout(): void{
           localStorage.clear();
           this.loggedIn = false;
           router.push("/login");
        },
    }

})
