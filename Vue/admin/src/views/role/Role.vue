<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openMenuModel(null)"
      >
        新增
      </el-button>
      <el-button
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="this.roleIdList.length == 0"
        @click="isDelete = true"
      >
        批量删除
      </el-button>
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <el-input
            clearable
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入角色名"
          style="width:200px"
          @keyup.enter.native="searchRoles"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchRoles"
        >
          搜索
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table
      border
      :data="roleList"
      @selection-change="selectionChange"
      v-loading="loading"
    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="roleLabel" label="权限标签" align="center">

      </el-table-column>
      <el-table-column prop="roleName" label="角色名" align="center" >
        <template slot-scope="scope">
          <el-tag type="success" size="big">
            {{ scope.row.roleName }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isDisable" label="禁用" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isDisable"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="changeDisable(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="150"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="220">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="openMenuModel(scope.row)">
            <i class="el-icon-edit" /> 菜单权限
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="openResourceModel(scope.row)"
          >
            <i class="el-icon-folder-checked" /> 资源权限
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:10px"
            @confirm="deleteRoles(scope.row.roleId)"
          >
            <el-button size="mini" type="text" slot="reference">
              <i class="el-icon-delete" /> 删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      class="pagination-container"
      background
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="current"
      :page-size="size"
      :total="count"
      :page-sizes="[10, 20]"
      layout="total, sizes, prev, pager, next, jumper"
    />

    <!-- 菜单对话框 -->
    <el-dialog :visible.sync="roleMenu" width="30%">
      <div class="dialog-title-container" slot="title" ref="roleTitle" />
      <el-form label-width="80px" size="medium" :model="roleForm">
        <el-form-item label="角色名">
          <el-input v-model="roleForm.roleName" style="width:250px" />
        </el-form-item>
        <el-form-item label="权限标签">
          <el-input v-model="roleForm.roleLabel" style="width:250px" />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-tree
            :data="menuList"
            :default-checked-keys="roleForm.menuIdList"
            :default-expanded-keys="[1]"
            :highlight-current="true"
            check-strictly
            show-checkbox
            node-key="id"
            ref="menuTree"
          >
            <span class="custom-tree-node" slot-scope="{ node, data }">
            <span v-if="!data.parentId">
              <el-tag type="primary" size="mini" style="position: relative;right: -5px">
                 <i :class="'iconfont '+ data.icon">
              </i>
              </el-tag>
              <el-tag type="success" style="position: relative;right: -15px" size="mini">
                {{ data.name }}
              </el-tag>
            </span>
             <span v-else>
               <i :class="'iconfont '+ data.icon"></i> {{ data.name }}
             </span>
         </span>
          </el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="roleMenu = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateRoleMenu">
          确 定
        </el-button>
      </div>
    </el-dialog>

    <!-- 资源对话框 -->
    <el-dialog :visible.sync="roleResource" width="30%" top="9vh">
      <div class="dialog-title-container" slot="title">修改资源权限</div>
      <el-form label-width="80px" size="medium" :model="roleForm">
        <el-form-item label="角色名">
          <el-input v-model="roleForm.roleName" style="width:250px" />
        </el-form-item>
        <el-form-item label="权限标签">
          <el-input v-model="roleForm.roleLabel" style="width:250px" />
        </el-form-item>
        <el-form-item label="资源权限">
          <el-tree
            :data="resourceList"
            :default-checked-keys="roleForm.resourceIdList"
            :default-expanded-keys="[1]"
            show-checkbox
            node-key="id"
            ref="resourceTree"
            :highlight-current="true"
          >
            <span class="custom-tree-node" slot-scope="{ node, data }">
            <span v-if="!data.parentId" style="color: #ff9900">
                {{ data.resourceName }}
            </span>
             <span v-else>
               <el-tag size="mini" :type="tagType(data.requestMethod)" style="margin-right: 15px">
                 {{data.requestMethod}}
               </el-tag>
               {{ data.resourceName }}
             </span>
         </span>
          </el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="roleResource = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateRoleResource">
          确 定
        </el-button>
      </div>
    </el-dialog>

    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="isDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteRoles(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>

  </el-card>
</template>

<script>
export default {
  created() {
    this.listRoles();
  },
  data: function() {
    return {
      loading: true,
      isDelete: false,
      roleList: [],
      roleIdList: [],
      keywords: null,
      current: 1,
      size: 10,
      count: 0,
      roleMenu: false,
      roleResource: false,
      resourceList: [],
      menuList: [],
      roleForm: {
        roleId: null,
        roleName: "",
        roleLabel: "",
        resourceIdList: [],
        menuIdList: []
      }
    };
  },
  computed:{
    tagType() {
      return function(type) {
        switch (type) {
          case "GET":
            return "";
          case "POST":
            return "success";
          case "PUT":
            return "warning";
          case "DELETE":
            return "danger";
        }
      };
    }
  },
  methods: {
    changeDisable() {

    },
    searchRoles() {
      this.current = 1;
      this.listRoles();
    },
    sizeChange(size) {
      this.size = size;
      this.listRoles();
    },
    currentChange(current) {
      this.current = current;
      this.listRoles();
    },
    selectionChange(roleList) {
      this.roleIdList = [];
      roleList.forEach(item => {
        this.roleIdList.push(item.roleId);
      });
    },
    deleteRoles(id) {
      let param = {};
      if (id == null) {
        param = { data: this.roleIdList };
      } else {
        param = { data: [id] };
      }
      this.axios.delete("/api/role/del/batch", param).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.isDelete = false;
      });
    },
    listRoles() {
      this.axios
        .get("/api/role/listRoles", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords
          }
        })
        .then(({ data }) => {
          this.roleList = data.data.recordList;
          this.count = data.data.count;
          this.loading = false;
        });
      this.axios.get("/api/resource/getResourcesInfoBack",{
        params:{
          keywords: ''
        }
      }).then(({ data }) => {
        this.resourceList = data.data;
      });
      this.axios.get("/api/menu/getAllMenuList").then(({ data }) => {
        this.menuList = data.data;
      });
    },
    openMenuModel(role) {
      this.$nextTick(function() {
        this.$refs.menuTree.setCheckedKeys([]);
      });
      this.$refs.roleTitle.innerHTML = role ? "修改角色" : "新增角色";
      if (role != null) {
        this.roleForm = JSON.parse(JSON.stringify(role));
      } else {
        this.roleForm = {
          roleId: null,
          roleName: "",
          roleLabel: "",
          resourceIdList: [],
          menuIdList: []
        };
      }
      this.roleMenu = true;
    },
    openResourceModel(role) {
      this.$nextTick(function() {
        this.$refs.resourceTree.setCheckedKeys([]);
      });
      this.roleForm = JSON.parse(JSON.stringify(role));
      this.roleResource = true;
    },
    saveOrUpdateRoleMenu() {
      if (this.roleForm.roleName.trim() == "") {
        this.$message.error("角色名不能为空");
        return false;
      }
      if (this.roleForm.roleLabel.trim() == "") {
        this.$message.error("权限标签不能为空");
        return false;
      }
      this.roleForm.resourceIdList = null;
      this.roleForm.menuIdList = this.$refs.menuTree
                  .getCheckedKeys()
                  .concat(this.$refs.menuTree.getHalfCheckedKeys());
      this.axios.post("/api/role/saveOrUpdateRolePermission", this.roleForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.roleMenu = false;
      });
    },
    saveOrUpdateRoleResource() {
      this.roleForm.menuIdList = null;
      this.roleForm.resourceIdList = this.$refs.resourceTree.getCheckedKeys();
      this.axios.post("/api/role/saveOrUpdateRolePermission", this.roleForm).then(({ data }) => {
        if (data.flag) {
          this.$notify.success({
            title: "成功",
            message: data.message
          });
          this.listRoles();
        } else {
          this.$notify.error({
            title: "失败",
            message: data.message
          });
        }
        this.roleResource = false;
      });
    },


  },
  watch: {
    keywords() {
      this.current = 1;
      this.listRoles();
    }
  }
};
</script>
