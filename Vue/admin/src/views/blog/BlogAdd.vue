<template>
  <el-card  style="margin: 0;padding: 0">
    <div class="title">{{ this.$route.name }}
      <span v-if="articleId != '0'">
        <el-tag type="success" size="big">
          {{articleId}} 号 文 章
        </el-tag>
      </span>
    </div>
    <!-- 文章标题 -->
    <div class="article-title-container" style=" display: flex;
    align-items: center;
    margin-bottom: 1.25rem;position: relative;bottom: -50px">
      <el-input
              v-model="article.articleTitle"
              size="medium"
              placeholder="输入文章标题"
      />
      <el-button
              type="primary"
              size="medium"
              class="save-btn"
              @click="shutdown"
      ><span style="font-weight: bolder" v-html="draft"></span>
      </el-button>
      <el-button

              type="success"
              size="medium"
              icon="el-icon-position"
              @click="addOrEdit = true"
              style="margin-left:10px"
           >
        <span style="font-weight: bolder">发布文章</span>
      </el-button>
    </div>

    <mavon-editor
            style="min-height: 500px;padding: 0;margin: 0;position: relative;bottom: -50px"
            v-model="article.articleContent"
            :ishljs="true"
            ref=md
            @change="change"
            @imgAdd="imgAdd"
    >
    </mavon-editor>

    <div>
      <!-- 添加文章对话框 -->
      <el-dialog :visible.sync="addOrEdit" width="40%" top="10vh">
        <div class="dialog-title-container" slot="title">
          上传文章
        </div>
        <!-- 文章数据 -->
        <el-form label-width="80px" size="medium" :model="article">
          <el-form-item label="文章分类">
            <el-select v-model="article.categoryId" placeholder="请选择分类">
              <el-option
                      v-for="item in categoryList"
                      :key="item.categoryId"
                      :label="item.categoryName"
                      :value="item.categoryId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="文章标签">
            <el-select
                    v-model="article.tagIdList"
                    multiple
                    placeholder="请选择标签"
            >
              <el-option
                      v-for="item in tagList"
                      :key="item.tagId"
                      :label="item.tagName"
                      :value="item.tagId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="上传封面">

            <el-upload
                    class="upload-cover"
                    action="/api/uploadImage"
                    drag
                    multiple
                    :on-success="uploadCover"
            >
              <i class="el-icon-upload" v-if="article.articleCover === ''" />
              <div class="el-upload__text" v-if="article.articleCover === ''">
                将图片拖到此处，或<em>点击上传</em>
              </div>
              <img
                      v-else
                      :src="article.articleCover"
                      width="360px"
                      height="180px"
               alt=""/>
            </el-upload>

          </el-form-item>
          <el-form-item label="置顶">
            <el-switch
                    v-model="article.top"
                    active-color="#13ce66"
                    inactive-color="#F4F4F5"
                    :active-value="true"
                    :inactive-value="false"
            />
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button @click="addOrEdit = false">取 消</el-button>
          <el-button type="danger" @click="saveOrUpdateArticle">
            发 表
          </el-button>
        </div>
      </el-dialog>
    </div>
  </el-card>
  

</template>

<script>
  import {getArticleById, getCategoryList, getTagList, saveOrUpdateArticle, uploadImage} from "@/api/blogAdd";
  export default {
    name: "BlogAdd",
    data(){
      return{
        draft: "自动保存(<span style='color: red'>OFF</span>)",
        autoSave:false,//自动保存草稿开关

        content:"",
        addOrEdit:false,//是否显示点击出来的对话框

        html:"",//转换的html
        images:[] ,//存储图片的数组

        //新增博客属性列表
        article:{
          articleId:null ,//博客id
          articleTitle: "" ,//博客标题
          articleContent:"",//博客内容
          articleCover:"",//博客封面
          categoryId:"",//分类id
          tagIdList:[] ,//标签id列表
          top:false,//是否置顶
          draft:false //是否存草稿
        },
        //分类列表
        categoryList:[],
        //标签列表
        tagList:[],
        articleId: 0,
      }
    },
    created() {
      this.article.articleTitle = ""
      this.article.articleContent = ""
      //获取当前路由路径
      const path = this.$route.path;
      // console.log(path)
      // console.log(2)
      //分割取得后面的id
      const arr = path.split("/");
      const articleId = arr[2];
      // console.log(arr[0])
      // console.log(arr[1])
      //如果文章id不是空 就根据当前id获取文章的信息
      if (articleId != null && articleId != "") {
        this.articleId = arr[2];
        getArticleById(articleId).then( res => {
          const cons = res.data;
          if(cons.flag){
            this.article=cons.data.article;
            // console.log(this.article)
          }
        })
      }
      //获取标签列表和分类列表
      this.getTagList();
      this.getCategoryList();
    },
    destroyed() {
      if (this.autoSave) {
        this.saveDraft();
        //自动保存草稿
      }
    },
    methods:{
      // 所有操作都会被解析重新渲染
      change(value, render){
        // render 为 markdown 解析后的结果[html]
        // 每一次输入都会被解析并渲染
        this.html = render;
        // console.log(this.html)
        // console.log(11)
      },
      //把图片上传到服务器，返回地址替换到md中
      imgAdd(pos,$file){
        let file=new FormData();
        file.append("file",$file)
        uploadImage( file ).then( res => {
          const cons = res.data;
          if (cons.flag) {
              let url = cons.data.url;
              // console.log(url);
              this.$refs.md.$img2Url(pos,url);
          } else {
            this.$message.error(cons.message)
          }
        }).catch( err => {
          this.$message.error("上传图片失败");
        })
      },
      shutdown() {
        if (this.autoSave) {
          this.autoSave = false;
          this.draft = "自动保存(<span style='color: red'>OFF</span>)"
        } else {
          this.autoSave = true;
          this.draft = "自动保存(<span style='color: #0fbe12'>ON</span>)"
        }
      },

      //获取标签列表
      async getTagList(){
        const res = await getTagList();
        const cons = res.data;
        // console.log(cons)
        if(cons.flag){
          this.tagList=cons.data.tagList;
        } else{
          this.$message.error(cons.message);
        }
      },
      //获取分类列表
      async getCategoryList(){
        const res = await getCategoryList();
        const cons = res.data;
        // console.log(cons)
        if(cons.flag){
          this.categoryList=cons.data.categoryList;
        } else{
          this.$message.error(cons.message);
        }
      },
      saveDraft() {
        if (this.article.articleTitle != null && this.article.articleTitle != "") {
          if (this.article.articleContent != null && this.article.articleContent != "") {
            saveOrUpdateArticle(this.article).then(res =>{
              const cons = res.data;
              // console.log(cons)
              if(cons.flag){
                this.$notify.success({
                  title: "成功",
                  message: "保存成功",
                });
              }else {
                this.$notify.error({
                  title: "失败",
                  message: "保存失败",
                });
              }
              this.article.articleId = cons.data.articleId;
              // console.log(this.article)
            })
          }
          else{
            this.$notify.error({
              title: "保存失败",
              message: "博客内容不能为空",
            });
          }
        }
        else{
          this.$notify.error({
            title: "保存失败",
            message: "博客标题不能为空",
          });
        }
      },
      //更新或者保存文章
      async saveOrUpdateArticle(){
        if (this.article.articleContent === null || this.article.articleContent === "") {
          this.addOrEdit=false
          this.$notify.error({
            title: "失败",
            message: "博客内容不能为空",
          });
        }
        else if (this.article.articleTitle === null || this.article.articleTitle === "") {
          this.addOrEdit=false
          this.$notify.error({
            title: "失败",
            message: "博客标题不能为空",
          });
        }
        else if (this.article.categoryId ==="" || this.article.tagIdList === null) {
          this.$message.error("请选择分类和标签")
        }
        else{
          const res = await saveOrUpdateArticle(this.article);
          const cons = res.data;
          if(cons.flag){
            this.$notify.success({
              title: "成功",
              message: "发布成功",
            });
          }else {
            this.$notify.error({
              title: "失败",
              message: "发布失败",
            });
          }
          this.addOrEdit=false;//是否显示点击出来的对话框
        }
      },
      uploadCover(res) {
        // console.log(res)
        this.article.articleCover = res.data.url;
      },



    }
  }
</script>

<style scoped>
  .save-btn {
    margin-left: 0.75rem;
    background: #fff;
    color: #6c7ef5;
  }
</style>
