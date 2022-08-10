import Vue from 'vue'
import VueRouter from 'vue-router'
import "nprogress/nprogress.css";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: '登 录',
    hidden: true,
    component: () => import('../views/Login')
  }
]

const createRouter = () =>
    new VueRouter({
      mode: "history",
      base: process.env.BASE_URL,
      routes
    });

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher;
}

export default router;


