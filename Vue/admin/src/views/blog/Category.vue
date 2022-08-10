<template>
  <el-card style="min-height: calc(100vh - 126px);">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
              type="success"
              size="small"
              icon="el-icon-plus"
              @click="openModel(null)"
      >
        新增
      </el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-deleteItem"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @onConfirm="delBatch"
      >
        <el-button type="danger" size="small" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <div style="margin-left:auto">
        <el-input
                v-model="categoryName"
                prefix-icon="el-icon-search"
                size="small"
                placeholder="请输入分类名"
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
    <el-table border :data="categoryList" @selection-change="handleSelectionChange">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 标签名 -->
      <el-table-column prop="tagName" label="分类名" align="center">
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.categoryName }}
          </el-tag>
        </template>
      </el-table-column>
      <!-- 标签创建时间 -->
      <el-table-column prop="createTime" label="创建时间" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | dateTime }}
        </template>
      </el-table-column>
      <!-- 标签更新时间 -->
      <el-table-column prop="updateTime" label="更新时间" align="center">
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.updateTime | dateTime }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="360">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="openModel(scope.row)" icon="el-icon-edit">
            编辑
          </el-button>
          <el-popconfirm
                  title="确定删除吗？"
                  style="margin-left:1rem"
                  @onConfirm="deleteCategory(scope.row.categoryId)"
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
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 10, 15]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        background
    />

    <!-- 编辑对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="30%">
      <div class="dialog-title-container" slot="title" ref="categoryTitle" />
      <el-form label-width="80px" size="medium" :model="categoryForm">
        <el-form-item label="分类名">
          <el-input style="width:220px" v-model="categoryForm.categoryName" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditCategory">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>


  import {addOrEditCategory, deleteCategory, listCategory,deleteBatchById} from "@/api/category";


  export default {
    created() {
      //获取分类列表
      this.getList() ;
    },
    name: "Category",
    data(){
      return{
        categoryForm:{
          categoryId:null,
          categoryName:"",
        } ,
        categoryList:[],
        multipleSelection:[],
        categoryName:"", //模糊搜索的标签名
        pageNum:1 ,//当前页
        pageSize:5 ,//一页的条数
        total:1,//总条数
        addOrEdit:false
      }
    },
    methods:{
      // 批量删除
      handleSelectionChange(val){
        console.log(val)
        this.multipleSelection = val
      },
      sizeChange(val) {
        //把val复制给size 重新在后台查询数据
        this.pageSize=val;
        this.getList();
      },
      //当页面改边的时候
      currentChange(val) {
        this.pageNum=val;
        this.getList();
      },
      async getList(){
        const res =  await listCategory(this.pageNum,this.pageSize,this.categoryName);
        const cons = res.data;
        // console.log(cons.data.categoryList.records)
        if(cons.flag){
          this.categoryList=cons.data.categoryList.records;
          this.total=cons.data.categoryList.total;
        }
      },
      openModel(category) {
        if (category != null) {
          this.categoryForm = JSON.parse(JSON.stringify(category));
          this.$refs.categoryTitle.innerHTML = "修改分类";

        } else {
          this.categoryForm.categoryId=null;
          this.categoryForm.categoryName = "";
          this.$refs.categoryTitle.innerHTML = "添加分类";
        }
        this.addOrEdit = true;
      },
      async delBatch(){
        console.log(111)
        let ids = this.multipleSelection.map(v => v.categoryId)  // [{}, {}, {}] => [1,2,3]
        const res = await deleteBatchById(ids);
        const cons = res.data;
        // console.log(cons)
        if(cons.flag){
          this.pageNum = 1;
          this.$message.success("批量删除成功");
          this.getList();
        }else{
          this.$message.error("批量删除失败");
        }
      },
      async addOrEditCategory() {
        if (this.categoryForm.categoryName.trim() === "") {
          this.$message.error("分类名不能为空");
          return false;
        }
        const res = await addOrEditCategory(this.categoryForm);
        const cons = res.data;
        if(cons.flag){
          this.$message.success("操作成功");
          this.getList();
        }else {
          this.$message.error("操作失败");
        }
        this.addOrEdit = false;
      },
      async deleteCategory(categoryId){
         const res =await deleteCategory(categoryId);
         const cons = res.data;
         if(cons.flag){
           this.pageNum = 1;
           this.getList();
           this.$message.success("删除当前分类成功");
         } else{
           this.$message.error("删除当前分类失败");
         }
      },
      reset(){
        this.categoryName = "";
        this.getList()
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
