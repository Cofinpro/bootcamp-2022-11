import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DetailView from '../views/DetailView.vue'
import axios from "axios";
import {el} from "vuetify/locale";
import LoginView from "@/views/LoginView.vue";

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
    {
      path: '/test',
      name: 'test',
      component: DetailView,
    },
    /*{
      path: '/details/:id',
      name: 'userDetails',
      component: UserDetails,
      props: true,
    },*/
    /*{
      path: '/logout',
      name: 'logout',
      component: Logout
    }*/
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
