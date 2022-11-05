<template>
  <div class="login-container" :style="cover">
    <div class="login-card">
      <div class="login-title">LOGIN</div>
      <!-- 登录表单 -->
      <el-form
        status-icon
        :model="loginForm"
        :rules="rules"
        ref="ruleForm"
        class="login-form"
      >
        <!-- 用户名输入框 -->
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            prefix-icon="el-icon-user-solid"
            placeholder="用户名"
            @keyup.enter.native="login"
          />
        </el-form-item>
        <!-- 密码输入框 -->
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            prefix-icon="iconfont el-icon-mymima"
            show-password
            placeholder="密码"
            @keyup.enter.native="login"
          />
        </el-form-item>
      </el-form>
      <!-- 登录按钮 -->
      <el-button type="primary" @click="login">登录</el-button>
    </div>
  </div>
</template>

<script>
import { generaMenu } from "@/assets/js/menu";
export default {
  data: function() {
    return {
      loginForm: {
        username: "",
        password: ""
      },
      rules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        password: [{ required: true, message: "密码不能为空", trigger: "blur" }]
      }
    };
  },
  methods: {
    login() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          const that = this;
          // eslint-disable-next-line no-undef
          let captcha = new TencentCaptcha(this.config.TENCENT_CAPTCHA, function(res) {
              if (res.ret === 0) {
                //发送登录请求
                let param = {
                  username: that.loginForm.username,
                  password: that.loginForm.password
                }
                that.axios.post("/api/login", param).then(({data}) => {
                  if (data.flag) {
                    // 登录后保存用户信息和角色信息
                    that.$store.commit("login", data.data.user);
                    that.$store.commit("saveRoleList", data.data.roles);
                    // 加载用户菜单
                    generaMenu();
                    that.$message.success("登录成功");
                    that.$router.push({ path: "/" });
                  } else {
                    that.$message.error(data.message);
                  }
                });
              }
            }
          );
          // 显示验证码
          captcha.show();
        } else {
          return false;
        }
      });
    },
  },
  computed:{
    cover() {
      return "background: url(" + this.$store.state.loginPage + ") center center / cover no-repeat";
    }
  }
};
</script>

<style scoped>
.login-container {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  background-color: #12b7f5;
}
.login-card {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  background: #fff;
  padding: 170px 60px 180px;
  width: 350px;
}
.login-title {
  color: #303133;
  font-weight: bolder;
  font-size: 1rem;
  text-align: center;
}
.login-form {
  margin-top: 1.2rem;
}
.login-card button {
  margin-top: 1rem;
  width: 100%;
}
</style>
