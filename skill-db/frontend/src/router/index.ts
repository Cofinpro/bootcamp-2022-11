import {createRouter, createWebHistory} from 'vue-router'
import LoginView from "@/views/LoginView.vue";
import HomeView from "@/views/HomeView.vue";
import axios from "axios";
import {el} from "vuetify/locale";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/login',
            name: 'Login',
            component: LoginView
        },
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
    ]
})

router.beforeEach((to, from, next) => {
    if (to.path !== '/login') {
        const refreshToken = localStorage.getItem("refresh_token");
        const username = localStorage.getItem("username");

        const user = {
            "refreshToken": refreshToken,
            "username": username,
        }

        axios.post("/api/token/verify", user)
            .then((result) => {
                if (result.data) {
                    next();
                }
                next('/login');
            }).catch((error) => {
            console.log(error.response)
        })
    } else {
        next();
    }


})

export default router
