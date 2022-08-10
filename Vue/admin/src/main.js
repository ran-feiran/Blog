import Vue from 'vue';
import App from './App.vue';
import router from './router/index';
import store from './store/index';
//element ui组件
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
//全局css
import './assets/css/global.css';
import "./assets/css/iconfont.css";
//axios组件
import axios from 'axios'
import VueAxios from 'vue-axios'
//markdown编辑器
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
//echarts
import ECharts from "vue-echarts";
import "echarts/lib/chart/line";
import "echarts/lib/chart/pie";
import "echarts/lib/chart/bar";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import * as echarts from 'echarts/lib/echarts.js'
// 腾讯图片验证
import config from "./assets/js/config";
// 加载页面进度条
import "nprogress/nprogress.css";
import moment from "moment"
import NProgress from "nprogress";
import "nprogress/nprogress.css";
// 导入加载动态路由和菜单
import { generaMenu } from "./assets/js/menu";

Vue.prototype.$moment = moment;
Vue.prototype.config = config
Vue.component("v-chart", ECharts);
Vue.use(VueAxios, axios) // 全局配置axios
Vue.use(mavonEditor);
Vue.use(ElementUI,{size:'small'})
Vue.config.productionTip = false

Vue.filter("date", function(value, formatStr = "YYYY-MM-DD") {
  return moment(value).format(formatStr);
});

Vue.filter("dateTime", function(value, formatStr = "YYYY-MM-DD hh:mm:ss") {
  return moment(value).format(formatStr);
});


NProgress.configure({
  easing: "ease", // 动画方式
  speed: 500, // 递增进度条的速度
  showSpinner: true, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3, // 初始化时的最小百分比
});

router.beforeEach((to, from, next) => {
  window.document.title = to.name
  NProgress.start();
  if (to.path === "/login") {
    next();
  }
  else {
    let username = store.state.userInfo.username;
    if(username === "" || username == null){
      next({ path: "/login" });
    } else {
      next();
    }
  }
})

router.afterEach(() => {
  NProgress.done();
});

new Vue({
  router,
  store,
  render: h => h(App),
  created() {
    if (store.state.userInfo.username != null) {
      generaMenu();
    }
  }
}).$mount('#app')
// 挂载路由守卫 就像后端的拦截器  没有那么多功能 这里只有简单的路由跳转
// beforeEach 每个请求之前  afterEach 每个请求之后 一般用beforeEach

