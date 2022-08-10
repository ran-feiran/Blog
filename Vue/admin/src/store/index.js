import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    collapse: false, //折叠按钮状态
    userRoles:[],//用户角色集合
    userInfo:{},//用户信息列表
    menuList:[],// 菜单列表
    tabList: [{ name: "首页", path: "/home" }],
  },
  mutations: {
    //登录保存用户信息
    login(state, user) {
      state.userInfo={} // 防止没有注销再次登录
      state.userInfo=user;
    },
    //保存用户的角色信息
    userRoles(state,userRole){
      state.userRoles = []
      state.userRoles=userRole;
    } ,
    menuList(state,menuList){
      state.menuList = []
      state.menuList=menuList
    },
    logout(state) {
      state.userInfo={}
      state.userRoles = []
      state.menuList = []
    },

    saveTab(state, tab) {
      if (state.tabList.findIndex(item => item.path === tab.path) === -1) {
        state.tabList.push({ name: tab.name, path: tab.path });
      }
    },

    // 移除标签页
    removeTab(state, tab) {
      let index = state.tabList.findIndex(item => item.path === tab.path);
      state.tabList.splice(index, 1);
    },

    // 重置标签页
    resetTab(state) {
      state.tabList = [{ name: "首页", path: "/home" }];
    },
    // 折叠菜单
    trigger(state) {
      state.collapse = !state.collapse;
    }
  },
  getters: {},
  actions: {},
  modules: {},
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
})
