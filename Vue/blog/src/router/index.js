import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/home/Home.vue";
import Article from "../views/article/Article.vue";
import Archive from "../views/archive/Archive.vue";
import Tag from "../views/tag/Tag.vue";
import Category from "../views/category/Category.vue";
import Link from "../views/link/Link.vue";
import About from "../views/about/About.vue";
import Message from "../views/message/Messsage.vue";
import ArticleList from "../components/ArticleList.vue";
import User from "../views/user/User.vue";
import OauthLogin from "../components/OauthLogin.vue";
import Error from "@/views/error/404";
import NProgress from "nprogress";
import "nprogress/nprogress.css";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Home
  },
  {
    path: "/articles/*",
    component: Article
  },
  {
    path: "/archives",
    component: Archive
  },
  {
    path: "/tags",
    component: Tag
  },
  {
    path: "/categories",
    component: Category
  },
  {
    path: "/category/*",
    component: ArticleList
  },
  {
    path: "/tag/*",
    component: ArticleList
  },
  {
    path: "/links",
    component: Link
  },
  {
    path: "/about",
    component: About
  },
  {
    path: "/message",
    component: Message
  },
  {
    path: "/user",
    component: User
  },
  {
    path: "/oauth/login/qq",
    component: OauthLogin
  },
  {
    path:"*",
    component: Error
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

NProgress.configure({
  easing: "ease", // 动画方式
  speed: 10, // 递增进度条的速度
  showSpinner: true, // 是否显示加载ico
  trickleSpeed: 10, // 自动递增间隔
  minimum: 0.01, // 初始化时的最小百分比
});

router.beforeEach((to, from, next) => {
  NProgress.start();
  next();
})

router.afterEach(() => {
  NProgress.done()
  window.scrollTo({
    top: 0,
    behavior: "instant"
  });
});


export default router;
