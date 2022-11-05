import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    collapse: true,
    tabList: [{ name: "首页", path: "/" }],
    userId: null,
    roleList: [],
    avatar: null,
    nickname: null,
    intro: null,
    webSite: null,
    userMenuList: [],
    loginPage: "",
  },
  mutations: {
    loadLoginPage(state, loginPage) {
      state.loginPage = loginPage;
    },
    login(state, user) {
      state.userId = user.userId;
      state.avatar = user.avatar;
      state.nickname = user.nickname;
      state.intro = user.intro;
      state.webSite = user.webSite;
    },
    logout(state) {
      state.userId = null;
      state.roleList = [];
      state.avatar = null;
      state.nickname = null;
      state.intro = null;
      state.webSite = null;
      state.userMenuList = [];
    },
    saveRoleList(state, roles) {
      state.roleList = roles;
    },
    saveTab(state, tab) {
      if (state.tabList.findIndex(item => item.path === tab.path) == -1) {
        state.tabList.push({ name: tab.name, path: tab.path });
      }
    },
    removeTab(state, tab) {
      var index = state.tabList.findIndex(item => item.name === tab.name);
      state.tabList.splice(index, 1);
    },
    resetTab(state) {
      state.tabList = [{ name: "首页", path: "/" }];
    },
    trigger(state) {
      state.collapse = !state.collapse;
    },
    saveUserMenuList(state, userMenuList) {
      state.userMenuList = userMenuList;
    },

    updateAvatar(state, avatar) {
      state.avatar = avatar;
    },
    updateUserInfo(state, user) {
      state.nickname = user.nickname;
      state.intro = user.intro;
      state.webSite = user.webSite;
    }
  },
  actions: {},
  modules: {},
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
});
