<template>
  <el-card style="min-height: calc(100vh - 126px);">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <template>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @onConfirm="delBatch"
      >
        <el-button type="danger" size="small" slot="reference" >批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      </template>

      <div style="margin-left:auto">
        <el-input
                v-model="articleTitle"
                prefix-icon="el-icon-search"
                size="small"
                placeholder="请输入博客标题"
                style="width:200px"
        />
        <el-button
                type="primary"
                size="small"
                icon="el-icon-search"
                style="margin-left:1rem"
                @click="getList"
        >
          搜索
        </el-button>
        <el-button
            type="warning"
            size="small"
            icon="el-icon-refresh"
            @click="reset"
        >
          重置
        </el-button>
      </div>
    </div>

    <!-- 表格展示 -->
    <el-table border :data="articleList" @selection-change="handleSelectionChange">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 标签名 -->
      <el-table-column prop="articleTitle" label="标题" align="center">
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.articleTitle }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="categoryName" label="分类" align="center" width="100px">
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.categoryName}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="tagName" label="标签" align="center" width="200px">
        <template slot-scope="scope">
            <el-tag
                    v-for="item of scope.row.tagName"
                    :key="item"
                    style="display: inline-flex;margin-right:0.2rem;font-size: 5px"
            >
              {{ item }}
            </el-tag>
        </template>
      </el-table-column>
      <!-- 标签创建时间 -->
      <el-table-column prop="createTime" label="创建时间" align="center">
        <template slot-scope="scope">
          <i   class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | dateTime }}
        </template>
      </el-table-column>
      <!-- 标签更新时间 -->
      <el-table-column prop="updateTime" label="更新时间" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.updateTime | dateTime  }}
        </template>
      </el-table-column>
<!--      置顶-->
<!--      <el-table-column prop="isTop" label="置顶" width="100" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <el-switch-->
<!--                  v-model="scope.row.isTop"-->
<!--                  active-color="#13ce66"-->
<!--                  inactive-color="#F4F4F5"-->
<!--                  :active-value="1"-->
<!--                  :inactive-value="0"-->
<!--          />-->
<!--        </template>-->
<!--      </el-table-column>-->
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="360">
        <template slot-scope="scope">
          <el-button type="success" size="mini"
                     @click="editArticle(scope.row.articleId)"
                     icon="el-icon-edit"
                     >
            编辑
          </el-button>
          <el-popconfirm
                  title="确定删除吗？"
                  style="margin-left:1rem"
                  @onConfirm="deleteArticle(scope.row.articleId)"
          >
            <el-button size="mini" type="danger" slot="reference" icon="el-icon-delete">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            background
    />

  </el-card>
</template>

<script>
  import {deleteArticleById, listArticle,deleteBatchById} from "@/api/blogList";
  export default {
    created() {
      //获取分类列表
      this.getList() ;
    },
    name: "blogList",
    data(){
      return{
        multipleSelection:[],
        articleList:[],
        articleTitle:"", //模糊搜索的
        pageNum:1 ,//当前页
        pageSize:5 ,//一页的条数
        total:1,//总条数
      }
    },
    methods:{
      // 批量删除
      handleSelectionChange(val){
        // console.log(val)
        this.multipleSelection = val
      },
      handleSizeChange(val) {
        //把val复制给size 重新在后台查询数据
        this.pageSize=val;
        this.getList();
      },
      //当页面改边的时候
      handleCurrentChange(val) {
        this.pageNum=val;
        this.getList();
      },
      async getList(){
        await  listArticle(this.pageNum,this.pageSize,this.articleTitle).then( res => {
          const cons = res.data;
          // console.log(cons)
         if(cons.flag){
           this.articleList=cons.data.articleList;
           this.total=cons.data.total;
         }
       }).catch(()=>{
         this.$message.error("请求博客列表失败");
       });
      },
      editArticle(id){
        this.$router.push({ path: "/article/" + id });
      },
      reset(){
        this.articleTitle = "";
        this.getList()
      },
     async  deleteArticle(articleId){
        const res =  await deleteArticleById(articleId);
        const cons = res.data;
        if(cons.flag){
          this.pageNum = 1
          this.$message.success("删除成功");
          this.getList();
        }else{
          this.$message.error("删除失败");
        }
      },
      delBatch(){
        let ids = this.multipleSelection.map(v => v.articleId)  // [{}, {}, {}] => [1,2,3]
        // console.log(ids)
        // console.log(112)
        deleteBatchById(ids).then((res)=>{
          const cons = res.data;
          if(cons.flag){
            this.pageNum = 1
            this.$message.success("批量删除成功");
            this.getList();
          }else{
            this.$message.error("批量删除失败");
          }
        })
      },
    },
  }
</script>

<style scoped>
  .operation-container {
    display: flex;
    align-items: center;
    margin-bottom: 1.25rem;
  }
  .ml-5{
    margin-left: 5px;
  }
</style>
