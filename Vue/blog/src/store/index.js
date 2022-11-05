import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    searchFlag: false,
    loginFlag: false,
    registerFlag: false,
    forgetFlag: false,
    emailFlag: false,
    drawer: false,  // 手机端
    loginUrl: "",
    userId: null,
    avatar: null,
    nickname: null,
    intro: null,
    webSite: null,
    loginType: null,
    email: null,
    articleLikeSet: [],
    commentLikeSet: [],
    blogInfo: {}  // 博客信息存储
  },
  mutations: {
    loginBlog(state, user) {
      state.userId = user.userId;
      state.avatar = user.avatar;
      state.nickname = user.nickname;
      state.intro = user.intro;
      state.webSite = user.webSite;
      state.email = user.email;
      state.articleLikeSet = user.articleLikeSet ? user.articleLikeSet : [];
      state.commentLikeSet = user.commentLikeSet ? user.commentLikeSet : [];
      state.email = user.email;
      state.loginType = user.loginType;
    },

    logoutBlog(state) {
      state.userId = null;
      state.avatar = null;
      state.nickname = null;
      state.intro = null;
      state.webSite = null;
      state.email = null;
      state.articleLikeSet = [];  // 文章点赞集合
      state.commentLikeSet = [];  // 评论点赞集合
      state.email = null;
      state.loginType = null;
    },
    updateUserInfo(state, user) {
      state.nickname = user.nickname;
      state.intro = user.intro;
      state.webSite = user.webSite;
    },
    saveEmail(state, email) {
      state.email = email;
    },
    updateAvatar(state, avatar) {
      state.avatar = avatar;
    },
    checkBlogInfo(state, blogInfo) {
      state.blogInfo = blogInfo;
    },
    closeModel(state) {
      state.registerFlag = false;
      state.loginFlag = false;
      state.searchFlag = false;
      state.forgetFlag = false;
      state.emailFlag = false;
    },
    commentLike(state, commentId) {
      let commentLikeSet = state.commentLikeSet;
      if (commentLikeSet.indexOf(commentId) != -1) {
        commentLikeSet.splice(commentLikeSet.indexOf(commentId), 1);
      } else {
        commentLikeSet.push(commentId);
      }
    },
    articleLike(state, articleId) {
      let articleLikeSet = state.articleLikeSet;
      if (articleLikeSet.indexOf(articleId) != -1) {
        articleLikeSet.splice(articleLikeSet.indexOf(articleId), 1);
      } else {
        articleLikeSet.push(articleId);
      }
    },
    saveLoginUrl(state, url) {
      state.loginUrl = url;  // 三方授权登录路径保存
    },

  },
  actions: {},
  modules: {},
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
});
