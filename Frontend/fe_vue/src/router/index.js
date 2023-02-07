import { createRouter, createWebHistory } from "vue-router";
import SignUp from "@/components/signup.vue";
import LogIn from "@/components/login.vue";
import RoadMap from "@/view/roadmap.vue";
import CourseView from "@/view/courseView.vue";
import UserHome from "@/view/userhome.vue";
import ChapterView from "@/view/chapterView.vue";
import RoadMapFactory from "@/view/roadmapFactory.vue"
// path: '/',
//     redirect: "/login",
//     component: LoginLayout,
const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("../view/home.vue"),
  },
  {
    path: "/roadmapFactory",
    name: "RoadMapFactory",
    component: RoadMapFactory,
  },
  {
    path: "/roadmap",
    name: "RoadMap",
    component: RoadMap,
  },
  {
    path: "/chapterView/:courseId",
    name: "ChapterView",
    component: ChapterView,
  },
  {
    path: "/courseView",
    name: "CourseView",
    component: CourseView,
  },
  {
    path: "/userhome",
    name: "UserHome",
    component: UserHome,
  },
  {
    path: "/signup",
    name: "SignUp",
    component: SignUp,
  },
  {
    path: "/login",
    name: "LogIn",
    component: LogIn,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

