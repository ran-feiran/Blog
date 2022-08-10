<template>
  <el-card class="main-card" >
    <div class="title">{{ this.$route.name }}</div>
    <mavon-editor v-model="about.content"
                  style="height:calc(100vh - 215px);position: relative;bottom: -30px"
    />
    <el-button
        plain
            type="success"
            size="mini"
            class="edit-btn"
            icon="el-icon-edit"
            @click="updateAbout"
            style="position: relative;bottom: -20px"
    >
      修改
    </el-button>
  </el-card>
</template>

<script>
  import {getAboutMe, updateAboutMe} from '@/api/about';
  export default {
    created() {
      this.getAbout();
    },
    data() {
      return {
        about :{
          id : 1,
          content: "",
        }
      };
    },
    methods: {
      async getAbout() {
        const res = await getAboutMe()
        const cons = res.data;
        // console.log(cons)
        if (cons.flag) {
          this.about.content = cons.data.content;
        } else {
          this.$message.error(cons.message);
        }
      },


      async updateAbout() {
        if (this.about.content === null || this.about.content === '') {
          this.$notify.error({
            title: "失败",
            message: '内容不能为空'
          });
        }
        else {
          // let param = new URLSearchParams();
          // param.append("aboutContent", this.aboutContent);
          const res = await updateAboutMe(this.about)
          const cons = res.data;
          if (cons.flag) {
            this.$notify.success({
              title: "成功",
              message: cons.message
            });
            this.getAbout();
          } else {
            this.$notify.error({
              title: "失败",
              message: cons.message
            });
          }
        }
      }
    }
  };
</script>

<style scoped>
  .edit-btn {
    float: right;
    margin: 1rem 0;
  }
</style>
