import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    /*{
      path: '/details/:id',
      name: 'userDetail',
      component: UserDetail
    },*/
    /*{
      path: '/logout',
      name: 'logout',
      component: Logout
    }*/
  ]
})

export default router
