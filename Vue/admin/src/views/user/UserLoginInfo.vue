<template>
  <el-card class="box-card" style=" margin-top: 10px">
    <el-form :inline="true" :model="UserQueryVO" class="demo-form-inline">
      <el-form-item label="昵称" >
          <el-input clearable v-model="UserQueryVO.nickname" placeholder="请输入内容" prefix-icon="el-icon-search"></el-input>
      </el-form-item>
      <el-form-item  style="float: right">
      <el-button icon="el-icon-refresh" @click="reset">重置</el-button>
      <el-button type="primary" icon="el-icon-search" @click="getUserLoginInfoList">查询</el-button>
      <el-button type="warning" icon="el-icon-download">导出</el-button>
      </el-form-item>
    </el-form>
<!--展示用户表格    y-->
    <el-table
            :data="UserLoginInfoList"
            border
            max-height="430px"
            style="width: 100%">
      <!--      头像t-->
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
      <!--昵称      n-->
      <el-table-column
              prop="nickname"
              label="昵称"
              width="100px"
              align="center"
      >
      </el-table-column>
<!--&lt;!&ndash;      用户登录方式&ndash;&gt;-->
            <el-table-column
                    prop="loginType"
                    label="登录方式"
                    width="280"
                    align="center"
                   >
              <template slot-scope="scope">
                <el-tag size="medium">{{ scope.row.loginType }}</el-tag>
              </template>
            </el-table-column>

       <!--   登录ip -->
      <el-table-column
              prop="ipAddress"
              label="登录ip"
              width="120px"
              align="center"
      >
      </el-table-column>
      <!--ip来源-->
      <el-table-column
              prop="ipSources"
              label="地址来源"
              width="120px"
              align="center"
      >
      </el-table-column>

      <el-table-column
              sortable
              align="center"
              prop="loginTime"
              label="登录时间"
              width="185"
              >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{scope.row.loginTime | dateTime}}</span>
        </template>
      </el-table-column>
      
      <el-table-column
              sortable
              prop="lastLoginTime"
              label="最近登录"
              width="185"
              align="center"
             >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{scope.row.lastLoginTime | dateTime}}</span>
        </template>
      </el-table-column>


      <el-table-column
              align="center"
              label="操作" >
        <template slot-scope="scope">
          <el-button  @click="deleteUser(scope.row.userLoginId)" type="danger" size="mini" icon="el-icon-delete">
            下线
          </el-button>
        </template>
        
      </el-table-column>
    </el-table>

    <el-pagination
            style="padding-top: 20px"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20, 30]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            background>
    </el-pagination>
  </el-card>
</template>

<script>
  import {userLoginInfoList, deleteUser} from "@/api/userLoginInfo";

  export default {
    name: "UserLoginInfo",
    created() {
      this.getUserLoginInfoList();
    },
    data() {
      return {
        //根据昵称查询
        UserQueryVO: {
          nickname:'',
        },
        //用户集合
        UserLoginInfoList:[],
        //每页显示条数
        pageSize:5,
        //总数据条数
        total:100,
        //当前第几页
        pageNum:1,

      }
    },
    methods: {
      //当每页条数改边的时候
      handleSizeChange(val) {
        //把val复制给size 重新在后台查询数据
        this.size=val;
        this.getUserLoginInfoList();
      },
      //当页面改边的时候
      handleCurrentChange(val) {
        this.current=val;
        this.getUserLoginInfoList();
      },
      reset(){
        this.UserQueryVO.nickname='';
        this.getUserLoginInfoList();
      },
      async getUserLoginInfoList() {
        const res = await userLoginInfoList(this.pageNum,this.pageSize,this.UserQueryVO.nickname);
        const cons = res.data;
        // console.log(cons)
        this.UserLoginInfoList=cons.data.userLoginInfoList;
        this.total=cons.data.total;
      },
      async deleteUser(id) {
        this.$confirm('此操作下线该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const res = await deleteUser(id);
          const cons = res.data;
          if (cons.flag) {
            this.$message.success(cons.message)
            this.getUserLoginInfoList()
          } else {
            this.$message.error(cons.message)
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消下线'
          });
        });
      }
    }
  }
</script>

<style scoped>

</style>
