<template>
  <el-card style="min-height: calc(100vh - 126px);">
    <div class="title">{{ this.$route.name }}</div>
    <el-button type="primary" icon="el-icon-menu" @click="addRole" style="position:relative;bottom: -40px"> 赋权菜单</el-button>
    <el-table border :data="roleList" style="position: relative;bottom: -50px">
      <el-table-column prop="roleName" label="角色名称" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.roleName === 'ADMIN'" type="success" size="big">
            {{ scope.row.roleName }}
          </el-tag>
          <el-tag v-else-if="scope.row.roleName === 'USER'" type="warning" size="big">
            {{ scope.row.roleName }}
          </el-tag>
          <el-tag v-else-if="scope.row.roleName === 'TEST'" type="primary" size="big">
            {{ scope.row.roleName }}
          </el-tag>
          <el-tag v-else type="info" size="big">
            {{ scope.row.roleName }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="description" label="描述" align="center" width="300px">
        <template slot-scope="scope">
          <el-tag>
            {{ scope.row.description}}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 列操作 -->
      <el-table-column label="操 作" align="center" >
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-menu" plain
                     @click="editAssignApi(scope.row.roleId,scope.row)"
          >
            接口权限
          </el-button>
          <el-button type="primary" size="small" icon="el-icon-switch-button" plain
                     @click="editAssignApi1(scope.row.roleId)"
          >
            管理界面
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="添加角色" :visible.sync="add">
      <el-card>
        <el-form :model="role">
          <el-form-item label="角色名" label-width="120px" >
            <el-input clearable v-model="role.roleName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" label-width="120px" >
            <el-input clearable v-model="role.description" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="add = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateRole">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="接口权限总览(限查看)" :visible.sync="menuVisible" width="35%" >
      <el-card>

        <el-form :model="role" style="position: relative;left: -50px">
          <el-form-item label="角色" label-width="120px" >
            <el-input clearable v-model="role.roleName" autocomplete="off" disabled></el-input>
          </el-form-item>
          <el-form-item label="描述" label-width="120px" >
            <el-input clearable v-model="role.description" autocomplete="off" disabled></el-input>
          </el-form-item>
        </el-form>
        <span>接口权限</span>
        <el-tree
            style="position: relative;right: -58px;top: -20px"
            :props="props"
            :data="apiListInfo"
            node-key="id"
            ref="tree"
            show-checkbox
            :highlight-current="true"
            :default-checked-keys="checks"
            :default-expanded-keys="expends"
        >
         <span class="custom-tree-node" slot-scope="{ node, data }">
            <span v-if="data.pid" >
              <el-tag type="primary" size="mini" style="position: relative;right: -5px">
                 {{ data.method }}
              </el-tag>
              <el-tag type="success" style="position: relative;right: -15px" size="mini">
                {{ data.name }}
              </el-tag>
            </span>
           <span v-else disabled>
             {{data.name}}
           </span>
         </span>
        </el-tree>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuVisible = false">取 消</el-button>
<!--        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>-->
      </div>
    </el-dialog>

  </el-card>
</template>

<script>
import {getRoleList, saveOrUpdateRole, listApiInfoBack, getAssignedApiIdByUserRoleId}
  from "@/api/swaggerApi";
  export default {
    created() {
      this.getRoleList();
    },
    name: "AssignAPI",
    data(){
      return{
        roleList:[],
        role:{},
        add:false,
        menuVisible:false,
        checks:[],
        expends:[],
        props:{
          label:'name'
        },
        apiListInfo:[],
      }
    },
    methods:{
      async getRoleList(){
        const res = await getRoleList();
        const cons = res.data;
        if(cons.flag){
          this.roleList=cons.data.roleList;
        } else {
          this.$message.error(cons.message)
        }
      },
      addRole() {
        this.$router.push({path : "/assignMenu"})
      },
      async editAssignApi(id,role){
        this.role = JSON.parse(JSON.stringify(role));
        this.expends = []
        this.checks = []
        const res = await listApiInfoBack()
        const cons = res.data;
        if(cons.flag){
          this.apiListInfo=cons.data.apiListInfo;
        } else {
          this.$message.error(cons.message)
        }


        const res1 = await getAssignedApiIdByUserRoleId(id);
        const cons1 = res1.data
        if(cons1.flag){
          this.checks = []
          this.checks=cons1.data.apiIdList;
          // console.log(this.checks)
        } else {
          this.$message.error(cons1.message)
        }


        this.menuVisible = true;
      },
      editAssignApi1(id) {
        this.$router.push({ path: "/swaggerApi/" + id });
      },
      async saveOrUpdateRole(){
        const res = await saveOrUpdateRole(this.role)
        const cons = res.data;
        if(cons.flag){
          this.$message.success(cons.message)
        } else {
          this.$message.error(cons.message)
        }
        this.dialogFormVisible = false;
        this.add = false;
        this.getRoleList()
      },
    },
  }
</script>

<style scoped>

</style>
