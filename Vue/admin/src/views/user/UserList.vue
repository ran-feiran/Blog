<template>
  <el-card class="box-card" style=" margin-top: 10px">
    <el-form :inline="true" :model="UserQueryVO" class="demo-form-inline">
     
      <el-form-item label="用户角色" >
      <el-select v-model="UserQueryVO.roleName" placeholder="请选择" prefix-icon="el-icon-search">
        <el-option
                clearable
                :key="1"
                label="所有用户"
                :value="null">
          <span style="float: left"> 所有用户</span>
          <span style="float: right; color: #8492a6; font-size: 13px">
            <span class="el-tag">{{total}}</span>
          </span>
        </el-option>
        <el-option
                clearable
                v-for="item in userRoleList"
                :key="item.roleName"
                :label="item.roleName"
                :value="item.roleName">
          <span >{{ item.roleName }}</span>
          <span style="float: right; color: #8492a6; font-size: 13px">
            <span class="el-tag">{{ item.size }}</span>
          </span>
        </el-option>
      </el-select>
      </el-form-item>

      <el-form-item label="昵称" >
          <el-input clearable v-model="UserQueryVO.nickname" placeholder="请输入内容" prefix-icon="el-icon-search"></el-input>
      </el-form-item>

      <el-form-item  style="float: right">
        <el-button type="primary" icon="el-icon-search" @click="getUserList">查询</el-button>
      <el-button icon="el-icon-refresh" @click="reset" type="success">重置</el-button>
      <el-button type="danger" icon="el-icon-plus" @click="dialogFormVisible = true">添加</el-button>
      <el-button type="warning" icon="el-icon-download">导出</el-button>
      </el-form-item>
    </el-form>
<!--展示用户表格    y-->
    <el-table

            :data="userList"
            border
            max-height="430px"
            style="width: 100%">
<!--      用户id-->
      <el-table-column
              align="center"
              prop="userId"
              label="#"
              width="40">
      </el-table-column>
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
<!--      用户身份-->
      <el-table-column
              prop="description"
              label="用户角色"
              width="120px"
              align="center"
      >
        <template slot-scope="scope">
          <el-tag size="medium" >{{ scope.row.roleName}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
              prop="nickname"
              label="昵称"
              width="150px"
              align="center"
      >
      </el-table-column>

      <el-table-column
          prop="webSite"
          label="个人网站"
          width="150px"
          align="center"
      >
      </el-table-column>

      <el-table-column
              sortable
              align="center"
              prop="createTime"
              label="创建时间"
              width="180"
              >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{scope.row.createTime | dateTime }}</span>
        </template>
      </el-table-column>
      
      <el-table-column
              sortable
              prop="updateTime"
              label="更新时间"
              width="180"
              align="center"
             >
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{scope.row.updateTime | dateTime }}</span>
        </template>
      </el-table-column>

      <el-table-column
              prop="isSilence"
              label="禁言"
              width="100"
              align="center"
      >
        <template slot-scope="scope">
          <el-switch
                  v-model="scope.row.silence"
                  @change="changeSilence(scope.row.silence,scope.row.userId)"
                  active-color="#13ce66"
                  inactive-color="">
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column
              align="center"
              label="操作"
               >
        <template slot-scope="scope">
          <el-button  type="primary"
                      size="mini"
                      icon="el-icon-edit"
                      @click="editUserInfo(scope.row)">编辑
          </el-button>
          <el-button  @click="deleteUser(scope.row.userId)" type="danger" size="mini" icon="el-icon-delete">删除</el-button>
        </template>
        
      </el-table-column>
    </el-table>

    <el-pagination
            style="padding-top: 20px"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="UserQueryVO.startIndex"
            :page-sizes="[2, 5, 10, 15]"
            :page-size="UserQueryVO.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            background>
    </el-pagination>

<!--   添加对话框 t-->
    <el-dialog title="修改用户信息" :visible.sync="editUser">
      <el-card>
        <el-form :model="editUserForm">
          <el-form-item label="头像" label-width="120px">
            <el-input clearable v-model="editUserForm.avatar" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="昵称" label-width="120px">
            <el-input clearable v-model="editUserForm.nickname " autocomplete="off"></el-input>
          </el-form-item>
          <!--        <el-form-item label="用户角色" label-width="120px">-->
          <!--          <el-select v-model="editUserForm.roleName" placeholder="请选择">-->
          <!--            <el-option-->
          <!--                    v-for="item in userRoleList"-->
          <!--                    :key="item.roleName"-->
          <!--                    :label="item.roleName"-->
          <!--                    :value="item.roleName">-->
          <!--            </el-option>-->
          <!--          </el-select>-->
          <!--        </el-form-item>-->

        </el-form>
      </el-card>

      <div slot="footer" class="dialog-footer">
        <el-button @click="editUser = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfoSure">确 定</el-button>
      </div>
    </el-dialog>



    <el-dialog title="增加用户" :visible.sync="dialogFormVisible">
      <el-card>
        <el-form :model="addUserForm">
          <el-form-item label="用户名" label-width="120px" >
            <el-input clearable v-model="addUserForm.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" label-width="120px">
            <el-input clearable v-model="addUserForm.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户头像url" label-width="120px">
            <el-input clearable v-model="addUserForm.avatar" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户昵称" label-width="120px">
            <el-input clearable v-model="addUserForm.nickname" autocomplete="off"></el-input>
          </el-form-item>

          <!--        <el-form-item label="用户角色" label-width="120px">-->
          <!--          <el-select v-model="addUserForm.roleName" placeholder="请选择">-->
          <!--            <el-option-->
          <!--                v-for="item in userRoleList"-->
          <!--                :key="item.roleName"-->
          <!--                :label="item.roleName"-->
          <!--                :value="item.roleName">-->
          <!--            </el-option>-->
          <!--          </el-select>-->
          <!--        </el-form-item>-->
        </el-form>

      </el-card>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </div>
    </el-dialog>

  </el-card>
</template>

<script>
  import {findUserList, fndRoleList, updateSilenceById, updateUserInfo, logicDeleteUserById, addUser} from "@/api/users";
  export default {
    name: "Users",
    created() {
      this.getUserList();
      this.getRoleList();
    },
    data() {
      return {
        UserQueryVO: {
          roleName:'',
          nickname:'',
          startIndex:1, //当前第几页
          pageSize:5, //每页显示条数
        },
        //用户集合
        userList: [],
        //用户角色集合
        userRoleList: [],
        //总数据条数
        total:100,
        editUserForm:{
          userId:1,
          avatar:"",
          nickname:"",
          // roleName:"",
        },
        addUserForm:{
          username:"",
          password:"",
          avatar:"",
          nickname:"",
          // roleName:"",
        },
        editUser: false,    //修改用户数据
        dialogFormVisible:false, //增加用户显示对话框
      }
    },
    methods: {
      //当每页条数改边的时候
      handleSizeChange(val) {
        //把val复制给size 重新在后台查询数据
        this.UserQueryVO.pageSize=val;
        this.getUserList();
      },
      //当页面改边的时候
      handleCurrentChange(val) {
        this.UserQueryVO.startIndex=val;
        this.getUserList();
      },
      async getUserList(){
       const res = await findUserList(this.UserQueryVO);
       const cons = res.data
       // this.$message.error(cons.message)
       // console.log(cons)
       this.userList=cons.data.userList;
       this.total=cons.data.total;

      },
      async getRoleList(){
        const res = await fndRoleList();
        const cons = res.data;
        // console.log(cons);
        this.$message.success(cons.message);
        this.userRoleList = cons.data.roleList;

      },
      reset(){
        this.UserQueryVO.nickname='';
        this.UserQueryVO.roleName='';
        this.getUserList()
      },
      // 点击修改按钮出现修改信息的对话框
      editUserInfo(user){
        this.editUser=true;
        this.editUserForm=JSON.parse(JSON.stringify(user));
      },
      // 点击确定修改按钮 交互数据
      async editUserInfoSure(){
        const res = await updateUserInfo(this.editUserForm);
        const cons = res.data;
        // console.log(cons);
        if(cons.flag){
          this.$message.success(cons.message);
          this.editUser=false;
          //刷新列表
          this.getUserList();
        }else{
          this.$message.error(cons.message);
        }
      },
      async addUser(){
        const  res =await addUser(this.addUserForm);
        const cons = res.data;
        // console.log(cons);
        if(cons.flag){
          this.$message.success(cons.message);
          this.getUserList();
        }else {
          this.$message.success(cons.message);
        }
        this.dialogFormVisible=false;
      },
      async changeSilence(flag,id){
        // console.log(flag)
        const res =await updateSilenceById(flag,id);
        const cons = res.data;
        // console.log(cons);
        if(cons.flag){
          if(flag === true){
            this.$message.success("用户已禁言");
          }else {
            this.$message.success("用户解除禁言");
          }
        }else{
          this.$message.error(cons.message);
        }
      },
      async deleteUser(id) {
        this.$confirm('此操作删除该用户, 是否继续?', '提示', {
           confirmButtonText: '确定',
           cancelButtonText: '取消',
           type: 'warning'
        }).  then( async () => {
          const res= await logicDeleteUserById(id);
          const cons = res.data;
          // console.log(cons);
          this.$message({
            type: cons.flag === true?'success':'error',
            message: cons.message
          });
          this.getUserList()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
    }
  }
  // async deleteUser(id){
  //    console.log(id);
  //    const {data}=await  deleteById(id);
  //
  // },
</script>

<style scoped>

</style>
