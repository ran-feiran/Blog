<template>
  <div>
    <!-- banner -->
    <div class="banner" :style="cover">
      <h1 class="banner-title">关于我</h1>
    </div>
    <!-- 内容 -->
    <v-row  class="article-container">
      <v-col md="9" cols="12">
        <!-- 关于我内容 -->
        <v-card class="article-wrapper">
          <!-- 博主头像 -->
          <div class="my-wrapper">
            <v-avatar size="110">
              <img class="author-avatar" :src="avatar" />
            </v-avatar>
          </div>
          <!--      介绍-->
          <div class="about-content markdown-body"
               v-html="aboutContent"
               ref="about">
          </div>
        </v-card>
      </v-col>
      <!-- 侧边功能 -->
      <v-col md="3" cols="12" class="d-md-block d-none" >
          <div style="position: sticky;top: 20px;">
            <!-- 文章目录 -->
            <v-card class="right-container">
              <div class="right-title">
                <svg class="icon" aria-hidden="true" style="font-size: 14px">
                  <use xlink:href="#icon-mulu"></use>
                </svg>
                <span style="margin-left:10px">目录</span>
              </div>
              <div id="toc" />
            </v-card>
          </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import Clipboard from "clipboard";
import markdownToHtml from "../../util/markdown";
import tocbot from "tocbot";
export default {
  created() {
    this.getAboutContent();
  },
  destroyed() {
    this.clipboard.destroy();
  },
  data: function() {
    return {
      aboutContent: "",
      clipboard: null,
      imgList: []
    };
  },
  methods: {
    getAboutContent() {
      const that = this;
      this.axios.get("/api/about/getAbout").then((res) => {
        const cons = res.data;
        this.aboutContent = markdownToHtml(cons.data);
        this.$nextTick(() => {
          // 添加代码复制功能
          this.clipboard = new Clipboard(".copy-btn");
          this.clipboard.on("success", () => {
            this.$toast({ type: "success", message: "复制成功" });
          });
          // 添加文章生成目录功能
          let nodes = this.$refs.about.children;
          if (nodes.length) {
            for (let i = 0; i < nodes.length; i++) {
              let node = nodes[i];
              let reg = /^H[1-4]{1}$/;
              if (reg.exec(node.tagName)) {
                node.id = i;
              }
            }
          }
          tocbot.init({
            tocSelector: "#toc", //要把目录添加元素位置，支持选择器
            contentSelector: ".about-content", //获取html的元素
            headingSelector: "h1, h2, h3", //要显示的id的目录
            hasInnerContainers: true,
            onClick: function(e) {
              e.preventDefault();
            }
          });
          // 添加图片预览功能
          const imgList = this.$refs.about.getElementsByTagName("img");
          for (let i = 0; i < imgList.length; i++) {
            this.imgList.push(imgList[i].src);
            imgList[i].addEventListener("click", function(e) {
              that.previewImg(e.target.currentSrc);
            });
          }
        });
      });
    },
    previewImg(img) {
      this.$imagePreview({
        images: this.imgList,
        index: this.imgList.indexOf(img)
      });
    }
  },
  computed: {
    cover() {
      var cover = "";
      this.$store.state.blogInfo.pageList.forEach(item => {
        if (item.pageLabel == "about") {
          cover = item.pageCover;
        }
      });
      return "background: url(" + cover + ") center center / cover no-repeat";
    },
    avatar() {
      return this.$store.state.blogInfo.websiteConfig.websiteAvatar;
    }
  }
};
</script>

<style scoped>
.right-container {
  padding: 20px 24px;
  font-size: 14px;
}
.right-title {
  display: flex;
  align-items: center;
  line-height: 2;
  font-size: 16.8px;
  margin-bottom: 6px;
}
.right-title i {
  font-weight: bold;
}
.my-wrapper {
  text-align: center;
}
.author-avatar {
  transition: all 0.5s;
}
.author-avatar:hover {
  transform: rotate(360deg);
}
.about-content {
  word-break: break-word;
  font-size: 1rem;
  line-height: 1.8;
}
</style>

<style lang="scss">
pre.hljs {
  padding: 12px 2px 12px 40px !important;
  border-radius: 5px !important;
  position: relative;
  font-size: 14px !important;
  line-height: 22px !important;
  overflow: hidden !important;
  &:hover .copy-btn {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  code {
    display: block !important;
    margin: 0 10px !important;
    overflow-x: auto !important;
    &::-webkit-scrollbar {
      z-index: 11;
      width: 6px;
    }
    &::-webkit-scrollbar:horizontal {
      height: 6px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 5px;
      width: 6px;
      background: #666;
    }
    &::-webkit-scrollbar-corner,
    &::-webkit-scrollbar-track {
      background: #1e1e1e;
    }
    &::-webkit-scrollbar-track-piece {
      background: #1e1e1e;
      width: 6px;
    }
  }
  .line-numbers-rows {
    position: absolute;
    pointer-events: none;
    top: 12px;
    bottom: 12px;
    left: 0;
    font-size: 100%;
    width: 40px;
    text-align: center;
    letter-spacing: -1px;
    border-right: 1px solid rgba(0, 0, 0, 0.66);
    user-select: none;
    counter-reset: linenumber;
    span {
      pointer-events: none;
      display: block;
      counter-increment: linenumber;
      &:before {
        content: counter(linenumber);
        color: #999;
        display: block;
        text-align: center;
      }
    }
  }
  b.name {
    position: absolute;
    top: 7px;
    right: 45px;
    z-index: 1;
    color: #999;
    pointer-events: none;
  }
  .copy-btn {
    position: absolute;
    top: 6px;
    right: 6px;
    z-index: 1;
    color: #ccc;
    background-color: #525252;
    border-radius: 6px;
    display: none;
    font-size: 14px;
    width: 32px;
    height: 24px;
    outline: none;
  }
}
</style>