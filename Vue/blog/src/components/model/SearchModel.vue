<template>
  <!-- 搜索框 -->
  <v-dialog v-model="searchFlag" max-width="600" :fullscreen="isMobile">
    <v-card class="search-wrapper" style="border-radius:4px">
      <div class="mb-3">
        <span class="search-title">搜索</span>
        <!-- 关闭按钮 -->
        <v-icon class="float-right" @click="closeSearchFlag">
          mdi-close
        </v-icon>
      </div>
      <!-- 输入框 -->
      <div class="search-input-wrapper">
        <svg class="icon" aria-hidden="true" style="margin-right: 4px">
          <use xlink:href="#icon-sousuo"></use>
        </svg>
        <input v-model="keywords" placeholder="输入文章标题或内容..." />
      </div>

      <!-- 搜索结果 -->
      <div class="search-result-wrapper">
        <hr class="divider" />
        <ul>
          <li class="search-reslut" v-for="item of articleList" :key="item.articleId">
            <!-- 文章标题 -->
            <a @click="goTo(item.articleId)" v-html="item.articleTitle" />
            <!-- 文章内容 -->
            <p
              class="search-reslut-content text-justify"
              v-html="item.articleContent"
            />
          </li>
        </ul>
        <!-- 搜索结果不存在提示 -->
        <div
          v-show="flag && articleList.length == 0"
          style="font-size:0.875rem"
        >
          <span style="font-weight: bolder">找不到您查询的内容：</span>
          <span style="color: #e80f16;font-weight: bold">{{ keywords }}</span>
        </div>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data: function() {
    return {
      keywords: "",
      articleList: [],
      flag: false
    };
  },
  methods: {
    goTo(articleId) {
      this.keywords = "";
      this.$store.state.searchFlag = false;
      this.$router.push({ path: "/articles/" + articleId });
    },
    closeSearchFlag() {
      this.keywords = "";
      this.$store.state.searchFlag = false;
    }
  },
  created() {
    console.log(
        "%c博客网站作者----ran-feiran，博客地址：https://www.ran-feiran.cn 咨询请加V:zr152527",
        "background-color:rgb(30,30,30);border-radius:4px;font-size:12px;padding:4px;color:rgb(220,208,129);"
    );
  },
  computed: {
    searchFlag: {
      set(value) {
        this.$store.state.searchFlag = value;
      },
      get() {
        return this.$store.state.searchFlag;
      }
    },
    isMobile() {
      const clientWidth = document.documentElement.clientWidth;
      if (clientWidth > 960) {
        return false;
      }
      return true;
    }
  },
  watch: {
    keywords(value) {
      // let md = require("markdown-it")();
      this.flag = value.trim() != "" ? true : false;
      this.axios
          .get("/api/article/search", {
            params: { current: 1, keywords: value }
          })
          .then((res) => {
            const cons = res.data;
            // 去掉markdown标签
            // cons.data.articleList.forEach(item => {
            //   item.articleContent = md
            //       .render(item.articleContent)
            //       .replace(/<\/?[^>]*>/g, "")
            //       .replace(/[|]*\n/, "")
            //       .replace(/&npsp;/gi, "");
            // });
            this.articleList = cons.data;
          });
    }
  }
};
</script>

<style scoped>
.search-wrapper {
  padding: 1.25rem;
  height: 100%;
}
.search-title {
  color: #49b1f5;
  font-size: 1.25rem;
  line-height: 1;
}
.search-input-wrapper {
  display: flex;
  padding: 5px;
  height: 35px;
  width: 100%;
  border: 2px solid #8e8cd8;
  border-radius: 2rem;
}
.search-input-wrapper input {
  width: 100%;
  margin-left: 5px;
  outline: none;
}
@media (min-width: 960px) {
  .search-result-wrapper {
    padding-right: 5px;
    height: 450px;
    overflow: auto;
  }
}
@media (max-width: 959px) {
  .search-result-wrapper {
    height: calc(100vh - 110px);
    overflow: auto;
  }
}
.search-reslut a {
  color: #555;
  font-weight: bold;
  border-bottom: 1px solid #999;
  text-decoration: none;
}
.search-reslut-content {
  color: #555;
  cursor: pointer;
  border-bottom: 1px dashed #ccc;
  padding: 5px 0;
  line-height: 2;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
.divider {
  margin: 20px 0;
  border: 2px dashed #d2ebfd;
}
</style>
