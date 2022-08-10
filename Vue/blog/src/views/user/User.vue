<template>
  <div>
    <div class="user-banner banner">
      <h1 class="banner-title">个人中心</h1>
    </div>
    <v-card class="blog-container">
      <div>
        <span class="info-title">基本信息</span>
      </div>
      <v-row class="info-wrapper">
        <v-col md="3" cols="12">
          <button id="pick-avatar">
            <v-avatar size="140">
              <img :src="this.$store.state.avatar"  alt=""/>
            </v-avatar>
          </button>
          <avatar-cropper
            @uploaded="uploadAvatar"
            trigger="#pick-avatar"
            upload-url="/api/user/avatar"
          />
        </v-col>
        <v-col md="7" cols="12">
          <v-text-field
            v-model="userInfo.nickname"
            label="昵称"
            placeholder="请输入您的昵称"
          />
          <v-text-field
              v-model="userInfo.email"
              class="mt-7"
              label="邮箱"
              placeholder="请输入您的邮箱"
          />
          <v-text-field
            v-model="userInfo.webSite"
            class="mt-7"
            label="个人网站"
            placeholder="(http | https)://你的网址"
          />
          <v-text-field
            v-model="userInfo.intro"
            class="mt-7"
            label="简介"
            placeholder="介绍下自己吧"
          />
          <v-btn @click="updateUserInfo" outlined class="mt-5">修改</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import AvatarCropper from "vue-avatar-cropper";
export default {
  components: { AvatarCropper },
  data: function() {
    return {
      userInfo: {
        userId: this.$store.state.userId,
        nickname: this.$store.state.nickname,
        intro: this.$store.state.intro,
        webSite: this.$store.state.webSite,
        email:this.$store.state.email,
      }
    };
  },
  methods: {
    updateUserInfo() {
      let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (!reg.test(this.userInfo.email)) {
        this.$toast({ type: "error", message: "邮箱格式不正确" });
        return false;
      }

      this.axios.put("/api/user/info", this.userInfo).then((res) => {
        const cons = res.data;
        if (cons.flag) {
          this.$store.commit("updateUserInfo", this.userInfo);
          this.$toast({ type: "success", message: "修改成功" });
        } else {
          this.$toast({ type: "error", message: cons.message });
        }
      });
    },

    // 更新头像
    uploadAvatar(data) {
      // console.log(data)
      if (data.flag) {
        this.$store.commit("updateAvatar", data.data.url);
        this.$toast({ type: "success", message: data.message });
      } else {
        this.$toast({ type: "error", message: data.message });
      }
    }
  }
};
</script>

<style scoped>
.user-banner {
  background: url("../../assets/img/11.jpg") center
    center / cover no-repeat;
}
.info-title {
  font-size: 1.25rem;
  font-weight: bold;
}
.info-wrapper {
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
#pick-avatar {
  outline: none;
}
</style>
