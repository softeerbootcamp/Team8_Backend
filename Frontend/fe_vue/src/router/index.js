import { createRouter, createWebHistory } from 'vue-router'
import SignUp from '@/components/signup.vue'
import LogIn from '@/components/login.vue'
import App from '../App.vue'
import UserHome from '@/view/userhome.vue'
const routes = [
  {
    path: '/',
    name: 'AppHome',
    redirect : App.vue
  },
  {
    path: '/userhome',
    name: 'UserHome',
    component: UserHome
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