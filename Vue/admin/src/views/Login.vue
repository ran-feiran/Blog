<template>
    <div class="container">
      <div class="login">
        <div class="login-title"><span style="color: #21D4FD;font-size: 25px" class="shadow-login" >博客</span>  <span style="color: #3EECAC;font-size: 25px" class="shadow-login">SPACE</span></div>
        <el-form :model="loginForm"
                 status-icon :rules="rules"
                 ref="loginForm"
                 class="login-form"
                >
          <el-form-item  prop="username">
            <el-input type="text" v-model="loginForm.username"
                      placeholder="用户名"
                      prefix-icon="el-icon-s-custom"
                      autocomplete="off">
            </el-input>
          </el-form-item>
          <el-form-item  prop="password">
            <el-input type="password"
                      v-model="loginForm.password"
                      placeholder="密码"
                      show-password
                      prefix-icon="el-icon-lock"
                      autocomplete="off">
            </el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('loginForm')" style="font-size: 15px;width: 100%; " >
              <span style="font-weight: bolder;color: rgba(220,226,234,0.94)" >登 录</span>
            </el-button>
            <span @click="resetForm" style="float: right;color: royalblue" >重置</span>
<!--            <el-button @click="resetForm()" style="float: left" type="success" round><span style="font-weight: bolder;;font-size: 15px">重置</span></el-button>-->
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>

<script>
  import {login} from "@/api/login";
  import { generaMenu } from "../assets/js/menu"
  export default {
    data() {
      return {
        loginForm: {
          username: '',
          password: '',
        },
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 10, message: '长度在 6 到 10 个字符', trigger: 'blur' }
          ],
        }
      };
    },
    methods: {
     async submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if(valid) {  //表单合法
            const that = this;
            const captcha = new TencentCaptcha(
                this.config.TENCENT_CAPTCHA,
                function (res) {
                  if (res.ret === 0) {//发送登录请求
                    login(that.loginForm).then(res => {
                      const cons = res.data;
                      // console.log(cons)
                      if(cons.flag){
                        that.$store.commit("login",cons.data.user);
                        that.$store.commit("userRoles", cons.data.roles[0]);
                        generaMenu()
                        // that.$message.success(cons.message);
                        that.$router.push({path: "/home"});
                      }else {
                        this.$message.error(cons.message);
                      }
                    }).catch(() => {
                      that.$message.error("账号或密码错误")
                    })
                  }
                }
            );
            // 显示验证码
            captcha.show();
          } else{
            return false;
          }
        });
      },
      resetForm() {
           this.loginForm.username='';
           this.loginForm.password='';
      }
    }
  }
</script>

<style>
  html,body{
    padding: 0;
    margin: 0;
  }
  .container{
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    position: absolute;
    background: url("../assets/1.jpg") no-repeat;
    background-size: cover;
  }
  .login{
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    background: #F2F6FC;
    padding: 170px 20px 180px;
    width: 400px;

  }
  .login-form{
    margin-top: 1.2rem;
    text-align: center;

  }
  .login-title {
    color: #303133;
    font-weight: bold;
    font-size: 1rem;
    text-align: left;
  }
  .shadow-login{
    text-shadow: 1px 2px blueviolet;
  }
</style>
