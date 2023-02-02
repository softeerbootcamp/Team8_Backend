import { createRouter, createWebHistory } from 'vue-router'
import SignUp from '@/components/signup.vue'
import LogIn from '@/components/login.vue'
import RoadMap from '@/view/roadmap.vue'
import CourseView from '@/view/courseView.vue'
// path: '/',
//     redirect: "/login",
//     component: LoginLayout,
const routes = [
  {
    path:'/',
    name : 'Home',
        component: () => import("../view/home.vue"),
  },
  {
    path: '/roadmap',
    name: 'RoadMap',
    component: RoadMap,
    children :[
      {
        path: '/courseView/:subjectDetail',
        name: 'CourseView',
        component: CourseView,
        props : true
      },
    ]
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