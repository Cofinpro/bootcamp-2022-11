import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DetailView from '../views/DetailView.vue'
import NewView from '../views/NewView.vue'
import EditView from '../views/EditView.vue'
import axios from "axios";
import {el} from "vuetify/locale";
import LoginView from "@/views/LoginView.vue";
import UserOverView from "@/views/UserOverView.vue"

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
    },
    {
        path: '/UserTest',
        name: 'usertest',
        component: UserOverView,
    },
    {
      path: '/detail/edit/:id',
      name: 'editView',
      component: EditView,
      props: true,
      meta: {
        title: 'Bearbeite dein Profil'
      }
    },
    /*{
      path: '/detail/:id',
      name: 'userDetails',
      component: UserDetails,
      props: true,
      meta: {
        title: 'Profil-Detailansicht'
      }
    },*/
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
