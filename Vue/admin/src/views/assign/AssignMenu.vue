<template>
  <el-card style="min-height: calc(100vh - 126px);">
    <div class="title">{{ this.$route.name }}</div>
    <el-button type="success" icon="el-icon-plus" @click="addRole" style="position:relative;bottom: -40px"> 添加</el-button>
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
          <el-button type="warning" size="small" icon="el-icon-menu" plain
                     @click="selectMenu(scope.row.roleName, scope.row.roleId, scope.row)"
          >
            分配菜单权限
          </el-button>
          <el-button type="primary" size="small" icon="el-icon-edit" plain
                     @click="editRole(scope.row)"
          >
            修 改
          </el-button>
          <el-popconfirm
              title="确定删除吗？"
              style="margin-left:10px"
              @onConfirm="deleteRole(scope.row.roleId)"
          >
            <el-button size="small" type="danger" plain slot="reference" icon="el-icon-delete" disabled>
              删 除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>

    <el-dialog title="修改角色" :visible.sync="dialogFormVisible">
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
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateRole">确 定</el-button>
      </div>
    </el-dialog>

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

    <el-dialog title="菜单权限分配" :visible.sync="menuVisible" width="30%" >
      <el-card>

        <el-form :model="role" style="position: relative;left: -50px">
          <el-form-item label="角色" label-width="120px" >
            <el-input clearable v-model="role.roleName" autocomplete="off" disabled></el-input>
          </el-form-item>
          <el-form-item label="描述" label-width="120px" >
            <el-input clearable v-model="role.description" autocomplete="off" disabled></el-input>
          </el-form-item>
        </el-form>

        <span >菜单权限</span>
        <el-tree
            style="position: relative;right: -58px;top: -20px"
            :props="props"
            :data="menuList"
            show-checkbox
            node-key="menuId"
            ref="tree"
            check-strictly
            :highlight-current="true"
            :default-checked-keys="checks"
            :default-expanded-keys="[1]"
        >

         <span class="custom-tree-node" slot-scope="{ node, data }">
            <span v-if="data.parentId != 0">
              <el-tag type="primary" size="mini" style="position: relative;right: -5px">
                 <i :class="data.menuIcon">
              </i>
              </el-tag>
              <el-tag type="success" style="position: relative;right: -15px" size="mini">
                {{ data.menuName }}
              </el-tag>
            </span>

             <span v-else>
               <i :class="data.menuIcon"></i> {{ data.menuName }}
             </span>
         </span>
        </el-tree>

      </el-card>

      <div slot="footer" class="dialog-footer">
        <el-button @click="menuVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>

    </el-dialog>

  </el-card>
</template>

<script>


import {getRoleList, saveOrUpdateRole, deleteRole} from "@/api/swaggerApi";
import {getMenuList, getAllMenuList, saveRoleMenu} from '@/api/menu'

export default {
    created() {
      this.getRoleList();
    },
    data(){
      return{
        roleList:[],
        role: {},
        dialogFormVisible: false,
        add: false,
        menuList: [],
        props:{
          label:'menuName'
        },
        expends:[],
        checks:[],
        menuVisible: false,
        roleId: 0,
      }
    },
    methods:{
      async selectMenu(roleName, roleId, role) {
        this.role = JSON.parse(JSON.stringify(role));
        this.roleId = roleId;
        this.expends = []
        this.checks = []
        const res = await getAllMenuList() // 全部菜单
        // const res = await getMenuList(roleName)
        const cons = res.data
        if (cons.flag) {
          this.menuList = cons.data.menuList;
          // 把后台返回的菜单数据处理成 id数组
          this.expends = []
          this.expends = this.menuList.map(v => v.menuId)
          // console.log(this.menuList)
          // console.log(1)
        } else {
          this.$message.error('获取所有菜单成功');
        }

        const result = await getMenuList(roleName)
        const constant = result.data;
        if (constant.flag) {
          this.checks = []
          this.menuVisible = true
          constant.data.menuList.forEach(item =>{
            this.checks.push(item.menuId)
            if (item.children.length != 0) {
              item.children.forEach(res =>{
                this.checks.push(res.menuId)
              })
            }
          })
          // console.log(this.checks)
        } else {
          this.$message.error(constant.message)
        }
        // console.log(this.checks)
      },
      async saveRoleMenu() {
        // console.log( this.$refs.tree.getCheckedKeys())
        // console.log(this.roleId)
        const res = await saveRoleMenu(this.roleId,  this.$refs.tree.getCheckedKeys());
        const cons = res.data;
        if (cons.flag) {
          this.$notify.success({
            title: "成功",
            message: cons.message,
          });
        } else {
          this.$notify.error({
            title: "失败",
            message: cons.message,
          });
        }
        this.menuVisible = false;
      },
      async getRoleList(){
        const res = await getRoleList();
        const cons = res.data;
        if(cons.flag){
          this.roleList=cons.data.roleList;
        } else {
          this.$message.error(cons.message)
        }
      },
      async deleteRole(id) {
        const res = await deleteRole(id)
        const cons = res.data
        if(cons.flag){
          this.$message.success(cons.message)
        } else {
          this.$message.error(cons.message)
        }
        this.getRoleList()
      },
      addRole() {
        this.role = {};
        this.add = true;
      },
      editRole(role) {
       this.role = JSON.parse(JSON.stringify(role));
       this.dialogFormVisible = true;
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
      editAssignApi(id){
        this.$router.push({ path: "/swaggerApi/" + id });
      }
    },
  }
</script>

<style scoped>

</style>
