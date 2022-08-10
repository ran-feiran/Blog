<template>
  <div>
    <!-- banner -->
    <div class="about-banner banner">
      <h1 class="banner-title">关于我</h1>
    </div>
    <!-- 关于我内容 -->
    <v-card class="blog-container">
      <!-- 博主头像 -->
      <div class="my-wrapper">
        <v-avatar size="110">
          <img class="author-avatar" :src="avatar"  alt=""/>
        </v-avatar>
      </div>
<!--      介绍-->
      <div class="about-content markdown-body" v-html="aboutContent"></div>
    </v-card>
  </div>
</template>

<script>
export default {
  created() {
    this.getAboutContent();
  },
  data: function() {
    return {
      aboutContent: ""
    };
  },
  methods: {
    getAboutContent() {
      this.axios.get("/api/getAbout").then((res) => {
        const cons = res.data;
        const MarkdownIt = require("markdown-it");
        const md = new MarkdownIt();
        this.aboutContent = md.render(cons.data.content);
      });
    }
  },
  computed: {
    avatar() {
      return this.$store.state.blogInfo.avatar;
    }
  }
};
</script>

<style scoped>
.my-wrapper {
  text-align: center;
}
.author-avatar {
  transition: all 0.5s;
}
.author-avatar:hover {
  transform: rotate(360deg);
}
.about-banner {
  background: url("../../assets/img/8.jpg") center center / cover
    no-repeat;
}
.about-content {
  word-break: break-word;
  font-size: 1rem;
  line-height: 1.8;
}
</style>
