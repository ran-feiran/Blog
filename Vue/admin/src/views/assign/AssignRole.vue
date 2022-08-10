<template>
  <el-card style="min-height: calc(100vh - 126px);">
    <div class="title">{{ this.$route.name }}</div>
    <el-form :inline="true"  class="demo-form-inline" style="float: right">
      <el-form-item label="昵称" >
        <el-input clearable v-model="nickname" placeholder="请输入内容" prefix-icon="el-icon-search"></el-input>
      </el-form-item>
      <el-form-item  >
        <el-button type="primary" icon="el-icon-search" @click="getUserList">查询</el-button>
        <el-button icon="el-icon-refresh" @click="reset" type="warning">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table border :data="userList">
      <el-table-column
              prop="avatar"
              label="头像"
              width="100px"
              align="center"
      >
        <template slot-scope="scope">
          <el-avatar :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>

      <el-table-column prop="username" label="用户名" align="center">
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.username }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="nickname" label="昵称" align="center">
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.nickname }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="简介" align="center"></el-table-column>

      <!-- 列操作 -->
      <el-table-column label="操作" align="center" >
        <template slot-scope="scope">
          <el-button
              plain
              type="primary"
              size="mini"
              icon="el-icon-s-tools"
              @click="editUserRole(scope.row)"
          >
            分配用户角色
          </el-button>
        </template>
      </el-table-column>
    </el-table>

<!--分页-->
    <el-pagination
        style="padding-top: 20px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[2, 5, 10, 15]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog title="修改角色" :visible.sync="dialogFormVisible" >
      <el-card>
        <el-form style="position: relative;right: -110px">
          <el-form-item  label-width="120px">
            <el-select v-model="user.roleName" placeholder="请选择">
                <el-option
                    v-for="item in userRoleList"
                    :key="item.roleName"
                    :label="item.roleName"
                    :value="item.roleName">
                </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </el-card>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateUserRole">确 定</el-button>
        </div>

    </el-dialog>

  </el-card>
</template>

<script>


import { getUserList, updateUserRole, } from "@/api/users";
import {getRoleList} from '@/api/swaggerApi'

  export default {
    created() {
      this.getUserList();
      this.getRoleList();
    },
    data(){
      return{
        //用户角色集合
        userRoleList:[],
        userList:[],
        current:1,
        size:10,
        nickname:'',
        total:0,
        dialogFormVisible: false,
        user: {},
      }
    },
    methods:{
      reset() {
        this.nickname = '';
        this.getUserList();
      },
      //当每页条数改边的时候
      handleSizeChange(val) {
        //把val复制给size 重新在后台查询数据
        this.size=val;
        this.getUserList();
      },
      //当页面改边的时候
      handleCurrentChange(val) {
        this.current=val;
        this.getUserList();
      },
      async getRoleList(){
        const res = await getRoleList();
        const cons = res.data;
        // console.log(cons);
        // this.$message.success(cons.message);
        this.userRoleList = cons.data.roleList;

      },
      async getUserList(){
        const res = await getUserList(this.current,this.size,this.nickname);
        const cons = res.data
        // this.$message.error(cons.message)
        // console.log(cons)
        this.userList=cons.data.userList;
        this.total=cons.data.total;
      },
      // 点击修改按钮出现修改信息的对话框
      editUserRole(user){
        this.user = {};
        this.dialogFormVisible=true;
        this.user=JSON.parse(JSON.stringify(user));
        // console.log(this.user)
      },
      async updateUserRole() {
        const res = await updateUserRole(this.user)
        const cons = res.data
        // console.log(cons)
        if (cons.flag) {
          this.$message.success(cons.message)
        } else {
          this.$message.error(cons.message)
        }
        this.dialogFormVisible = false;
        this.getUserList()
      }
    },
  }
</script>

<style scoped>

</style>
