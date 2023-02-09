import { createRouter, createWebHistory } from "vue-router";
import SignUp from "@/components/signup.vue";
import LogIn from "@/components/login.vue";
import RoadMap from "@/view/roadmap.vue";
import CourseView from "@/view/courseView.vue";
import UserHome from "@/view/userhome.vue";
import ChapterView from "@/view/chapterView.vue";
import RoadMapFactory from "@/view/roadmapFactory.vue"
import AdminHome from "@/view/adminHome.vue"
import ExamView from "@/view/examView.vue"
import ExamSubmitView from "@/view/examSubmitView.vue"
import ExamPurchaseView from "@/view/examPurchaseView.vue"
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
    path: "/examview/:examId",
    name: "ExamView",
    component: ExamView,
  },
  {
    path: "/examsubmitview/:examId",
    name: "ExamSubmitView",
    component: ExamSubmitView,
  },
  {
    path: "/adminhome",
    name: "AdminHome",
    component: AdminHome,
  },

  {
    path: "/roadmapFactory/:userEmail",
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
  {
    path: "/exampurchaseview/:examId",
    name: "ExamPurchaseView",
    component: ExamPurchaseView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;

