<template>
  <v-dialog v-model="registerFlag" :fullscreen="isMobile" max-width="460">
    <v-card class="login-container" style="border-radius:4px">
      <v-icon class="float-right" @click="registerFlag = false">
        mdi-close
      </v-icon>
      <div class="login-wrapper">
        <!-- 用户名 -->
        <v-text-field
          v-model="email"
          label="邮箱"
          placeholder="请输入您的邮箱号"
          clearable
          @keyup.enter="register"
        />
        <!-- 密码 -->
        <v-text-field
          v-model="password"
          class="mt-7"
          label="密码"
          placeholder="请输入您的密码"
          @keyup.enter="register"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          @click:append="show = !show"
        />
        <!-- 验证码 -->
        <div class="mt-7 send-wrapper">
          <v-text-field
                  maxlength="6"
                  v-model="code"
                  label="验证码"
                  placeholder="请输入6位验证码"
                  @keyup.enter="register"
          />
          <v-btn text small :disabled="flag" @click="sendCode">
            {{ codeMsg }}
          </v-btn>
        </div>
        <!-- 注册按钮 -->
        <v-btn
          block
          color="red"
          style="color:#fff;margin-top: 17px"
          @click="register"
        >
          注册
        </v-btn>
        <!-- 登录 -->
        <div style="margin-top: 15px">
          已有账号？<span @click="openLogin" style="color: #4ab1f4">登录</span>
        </div>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data: function() {
    return {
      username: "",
      email:"",
      code: "",
      password: "",
      flag: true,
      codeMsg: "发送",
      time: 60,
      show: false
    };
  },
  methods: {
    openLogin() {
      this.$store.state.registerFlag = false;
      this.$store.state.loginFlag = true;
    },
    register() {
      let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      // if(this.username.length == 0){
      //   this.$toast({ type: "error", message: "用户名不能为空" });
      //   return false;
      // }
      if (!reg.test(this.email)) {
        this.$toast({ type: "error", message: "邮箱格式不正确" });
        return false;
      }
      if (this.password.trim().length < 5) {
        this.$toast({ type: "error", message: "密码不能少于6位" });
        return false;
      }
      if (this.password.trim().length > 15) {
        this.$toast({ type: "error", message: "密码不能多于15位" });
        return false;
      }
      if (this.code.trim().length != 6) {
        this.$toast({ type: "error", message: "请输入6位验证码" });
        return false;
      }
      const user = {
        username: this.email,
        email:this.email,
        password: this.password,
        code: this.code
      };
      this.axios.post("/api/user/registerUser", user).then((res) => {
        const cons = res.data;
        if (cons.flag) {
          this.username="";
          this.email="";
          this.password="";
          this.code="";
          this.$store.commit("closeModel");
          this.$toast({ type: "success", message: cons.message });
        } else {
          this.$toast({ type: "error", message: cons.message });
        }
      });
    },
    sendCode() {
      const that = this;
      let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (!reg.test(this.email)) {
        this.$toast({ type: "error", message: "邮箱格式不正确" });
        return false;
      }
          //发送邮件
      // eslint-disable-next-line no-undef
      let captcha = new TencentCaptcha(this.config.TENCENT_CAPTCHA_BLOG, function(res) {
            if (res.ret === 0) {
                that.axios
                  .get("/api/user/sendEmailCode", {
                    params: { email: that.email }
                  })
                  .then((res) => {
                    const cons = res.data
                    if (cons.flag) {
                      that.countDown();
                      that.$toast({ type: "success", message: cons.message });
                    } else {
                      that.$toast({ type: "error", message: cons.message });
                    }
                  });
            }
      });
      // 显示验证码
      captcha.show();
    },
    countDown() {
      this.flag = true;
      this.timer = setInterval(() => {
        this.time--;
        this.codeMsg = this.time + "s";
        if (this.time <= 0) {
          clearInterval(this.timer);
          this.codeMsg = "发送";
          this.time = 60;
          this.flag = false;
        }
      }, 1000);
    },
  },
  computed: {
    registerFlag: {
      set(value) {
        this.$store.state.registerFlag = value;
      },
      get() {
        return this.$store.state.registerFlag;
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
  watch:{
    email(value) {
      let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (reg.test(value)) {
        this.flag = false;
      } else {
        this.flag = true;
      }
    }
  }
}
</script>


