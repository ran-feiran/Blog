<template>
<div>
  <span class="pos">404</span>
  <div style="width: 200px;" >
    <v-btn
        block
        class="pos1"
        color="blue"
        style="color:#fff"
        @click="$router.push('/')"
    >
      返回首页
    </v-btn>
  </div>
</div>
</template>

<script>
export default {
  name: "404"
}
</script>

<style scoped>
.pos{
  position: relative;
  right: -720px;
  bottom: -200px;
  font-size: 80px;
}
.pos1{
  position: relative;
  right: -685px;
  bottom: -200px;
  font-size: 20px;
}
</style>