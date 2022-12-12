import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DetailView from '../views/DetailView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/test',
      name: 'test',
      component: DetailView,
    }
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

export default router
