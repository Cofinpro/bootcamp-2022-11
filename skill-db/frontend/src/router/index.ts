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

/**
 * Checks before each request (except to "/login") if the refreshToken is still valid.
 * In case the token is not valid anymore, the user will be redirected to the login page and
 * has to log in again.
 */
router.beforeEach((to, from, next) => {
    if (to.path !== '/login') {
        const refreshToken = localStorage.getItem("refresh_token");
        const username = localStorage.getItem("username");

        const user = {
            "refreshToken": refreshToken,
            "username": username,
        }

        axios.post("/api/v1/token/verify", user)
            .then((result) => {
                if (result.data) {
                    next();
                    return;
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
