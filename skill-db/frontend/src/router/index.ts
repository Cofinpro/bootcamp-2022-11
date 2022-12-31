import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import DetailView from '../views/DetailView.vue'
import NewView from '../views/NewView.vue'
import EditView from '../views/EditView.vue'
import LoginView from "@/views/LoginView.vue";
import UserOverView from "@/views/UserOverView.vue"
import {useAuthStore} from "@/stores/auth";

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
        path: '/details/new',
        name: 'new',
        component: NewView,
        meta: {
            title: 'Neues Profil erstellen'
        }
    },
    {
        path: '/admin/users',
        name: 'userOverview',
        component: UserOverView,
        meta: {
            title: 'Nutzerübersicht'
        }
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
    {
      path: '/detail/:id',
      name: 'userDetails',
      component: DetailView,
      props: true,
      meta: {
        title: 'Profil-Detailansicht'
      }
    },
  ]
})

/**
 * Checks before each request (except to "/login") if the refreshToken is still valid.
 * In case the token is not valid anymore, the user will be redirected to the login page and
 * has to log in again.
 */
router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    if (to.path !== '/login') {
        if (authStore.isLoggedIn()) {
            if (authStore.isAdmin()) {
                next();
            } else if (! to.path.includes('/admin')) {
                next();
            } else {
                next('/');
            }
        } else {
            next('/login');
        }
    } else {
        next();
    }

})

export default router