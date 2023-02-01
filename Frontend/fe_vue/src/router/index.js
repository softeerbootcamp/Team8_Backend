import { createRouter, createWebHistory } from 'vue-router'
import SignUp from '@/components/signup.vue'
import LogIn from '@/components/login.vue'
import RoadMap from '@/view/roadmap.vue'
// path: '/',
//     redirect: "/login",
//     component: LoginLayout,
const routes = [
  {
    path: '/roadmap',
    name: 'RoadMap',
    component: RoadMap
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/login',
    name: 'LogIn',
    component: LogIn
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router