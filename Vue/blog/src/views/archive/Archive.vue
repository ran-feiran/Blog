<template>
  <div>
    <!-- banner -->
    <div class="banner" :style="cover">
      <h1 class="banner-title">归档</h1>
    </div>
    <!-- 归档列表 -->
    <v-card class="blog-container">
      <timeline>
        <timeline-title> 目前共计{{ count }}篇文章，继续加油 </timeline-title>
        <timeline-item v-for="item of archiveList" :key="item.articleId">
          <!-- 日期 -->
          <span class="time">{{ item.createTime | date }}</span>
          <!-- 文章标题 -->
          <router-link
              class="route-link"
            :to="'/articles/' + item.articleId"
            style="text-decoration: none"
          >
            {{ item.articleTitle }}
          </router-link>
        </timeline-item>
      </timeline>

      <!-- 分页按钮 -->
      <v-pagination
        color="#00C4B6"
        v-model="current"
        :length="Math.ceil(count / 10)"
        total-visible="7"
      />
    </v-card>
  </div>
</template>

<script>
import { Timeline, TimelineItem, TimelineTitle } from "vue-cute-timeline";
export default {
  created() {
    this.listArchives();
  },
  components: {
    Timeline,
    TimelineItem,
    TimelineTitle
  },
  data: function() {
    return {
      current: 1,
      count: 0,
      archiveList: []
    };
  },
  methods: {
    listArchives() {
      this.axios
        .get("/api/article/archives", {
          params: { current: this.current }
        })
        .then((res) => {
          const cons = res.data;
          // console.log(cons)
          this.archiveList = cons.data.recordList;
          this.count = cons.data.count;
        });
    }
  },
  watch: {
    current(value) {
      this.axios
        .get("/api/article/archives", {
          params: { current: value }
        })
        .then((res) => {
          const cons = res.data;
          // console.log(cons)
          this.archiveList = cons.data.recordList;
          this.count = cons.data.count;
        });
    }
  },
  computed:{
    cover() {
      var cover = "";
      this.$store.state.blogInfo.pageList.forEach(item => {
        if (item.pageLabel == "archive") {
          cover = item.pageCover;
        }
      });
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  }
};
</script>

<style scoped>
.route-link:hover{
  color: #ba80c9;
}
.time {
  font-size: 0.75rem;
  color: rgb(40, 45, 204);
  margin-right: 1rem;
}
</style>
