import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DetailView from '../views/DetailView.vue'
import NewView from '../views/NewView.vue'
import axios from "axios";
import {el} from "vuetify/locale";
import LoginView from "@/views/LoginView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: LoginView,
      meta: {
        title: 'Login'
      }
    },
    {
      path: '/',
      name: 'home',
      component: HomeView,
      meta: {
          title: 'Profilübersicht'
      }
    },
    {
      path: '/test',
      name: 'test',
      component: DetailView,
      meta: {
          title: 'Detail-Test'
      }
    },
    {
        path: '/details/new',
        name: 'new',
        component: NewView,
        meta: {
            title: 'Neues Profil erstellen'
        }
    }
    /*{
      path: '/details/:id',
      name: 'userDetails',
      component: UserDetails,
      props: true,
      meta: {
        title: Profil-Detailansicht
      }
    },*/
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
