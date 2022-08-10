<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <iframe
            X-Frame-Options
            src="/api/swagger-ui.html"
            class="iframe"
    />
  </el-card>
</template>

<style scoped>
  .iframe {
    height: calc(100vh - 170px);
    width: 100%;
    border: none;
    scrolling: yes;
    position: relative;
    bottom: -40px;
  }
</style>
