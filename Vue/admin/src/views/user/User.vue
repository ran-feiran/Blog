<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <!-- 条件筛选 -->
      <div style="margin-left:auto">
        <!-- 登录方式 -->
        <el-select
          clearable
          v-model="loginType"
          placeholder="请选择登录方式"
          size="small"
          style="margin-right:1rem"
        >
          <el-option
            v-for="item in typeList"
            :key="item.type"
            :label="item.desc"
            :value="item.type"
          />
        </el-select>
        <el-input
            clearable
          v-model="keywords"
          prefix-icon="el-icon-search"
          size="small"
          placeholder="请输入昵称"
          style="width:200px"
          @keyup.enter.native="searchUsers"
        />
        <el-button
          type="primary"
          size="small"
          icon="el-icon-search"
          style="margin-left:1rem"
          @click="searchUsers"
        >
          搜索
        </el-button>
        <el-button
            type="warning"
            size="small"
            icon="el-icon-refresh"
            style="margin-left:1rem"
            @click="rest"
        >
          重置
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table border :data="userList" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column
        prop="linkAvatar"
        label="头像"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <img :src="scope.row.avatar" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column
        prop="nickname"
        label="昵称"
        align="center"
        width="140"
      />
      <el-table-column
        prop="loginType"
        label="登录方式"
        align="center"
        width="80"
      >
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.loginType == 0">邮箱</el-tag>
          <el-tag v-if="scope.row.loginType == 1">QQ</el-tag>
          <el-tag type="danger" v-if="scope.row.loginType == 2">微博</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="roleList" label="用户角色" align="center">
        <template slot-scope="scope">
          <el-tag
            v-for="(item, index) of scope.row.roleList"
            :key="index"
            style="margin-right:4px;margin-top:4px"
          >
            {{ item.roleName }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isSilence" label="禁用" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isSilence"
            active-color="#13ce66"
            inactive-color="#F4F4F5"
            :active-value="1"
            :inactive-value="0"
            @change="changeDisable(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="ipAddress"
        label="登录ip"
        align="center"
        width="140"
      />
      <el-table-column
        prop="ipSource"
        label="登录地址"
        align="center"
        width="140"
      />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="130"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <el-table-column
        prop="lastLoginTime"
        label="上次登录时间"
        width="130"
        align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.lastLoginTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="100">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="openEditModel(scope.row)"
          >
            编辑
          </el-button>
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

    <!-- 修改对话框 -->
    <el-dialog :visible.sync="isEdit" width="30%">
      <div class="dialog-title-container" slot="title">
        修改用户
      </div>
      <el-form label-width="60px" size="medium" :model="userForm">
        <el-form-item label="昵称">
          <el-input v-model="userForm.nickname" style="width:220px" />
        </el-form-item>
        <el-form-item label="角色">
          <el-checkbox-group v-model="roleIdList">
            <el-checkbox
              v-for="item of userRoleList"
              :key="item.id"
              :label="item.id"
            >
              {{ item.roleLabel }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="isEdit = false">取 消</el-button>
        <el-button type="primary" @click="editUserRole">
          确 定
        </el-button>
      </div>
    </el-dialog>

  </el-card>
</template>

<script>
export default {
  created() {
    this.listUsers();
    this.listRoles();
  },
  data: function() {
    return {
      loading: true,
      isEdit: false,
      userForm: {
        userId: null,
        nickname: ""
      },
      loginType: null,
      userRoleList: [],
      roleIdList: [],
      userList: [],
      typeList: [
        {
          type: 0,
          desc: "邮箱"
        },
        {
          type: 1,
          desc: "QQ"
        },
        // {
        //   type: 2,
        //   desc: "微博"
        // }
      ],
      keywords: null,
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    rest() {
      this.current = 1
      this.keywords = ''
      this.listUsers()
    },
    searchUsers() {
      this.current = 1;
      this.listUsers();
    },
    sizeChange(size) {
      this.size = size;
      this.listUsers();
    },
    currentChange(current) {
      this.current = current;
      this.listUsers();
    },
    changeDisable(user) {
      this.axios.put("/api/user/updateSilenceById", {
        userId: user.userId,
        isSilence: user.isSilence
      }).then(({data}) => {
        if (data.flag) {
          this.$notify.success({
            title: '成功',
            message: data.message
          })
        } else {
          this.$notify.error({
            title: '失败',
            message: data.message
          })
        }
      })
    },
    openEditModel(user) {
      this.roleIdList = [];
      this.userForm = JSON.parse(JSON.stringify(user));
      this.userForm.roleList.forEach(item => {
        this.roleIdList.push(item.roleId);
      });
      this.isEdit = true;
    },

    editUserRole() {
      this.userForm.roleIdList = this.roleIdList;
      if (this.userForm.roleIdList.length == 0) {
        this.$message.error('请至少选择一个角色资源')
        return false;
      }
      if (this.userForm.roleIdList.length > 1) {
        this.$message.error('资源选择过多')
        return false;
      }
      this.axios
        .put("/api/user/updateUserRole", this.userForm)
        .then(({ data }) => {
          if (data.flag) {
            this.$notify.success({
              title: "成功",
              message: data.message
            });
            this.listUsers();
          } else {
            this.$notify.error({
              title: "失败",
              message: data.message
            });
          }
          this.isEdit = false;
        });
    },

    listUsers() {
      this.axios
        .get("/api/user/getUserList", {
          params: {
            current: this.current,
            size: this.size,
            keywords: this.keywords,
            loginType: this.loginType
          }
        })
        .then(({ data }) => {
          this.userList = data.data.recordList;
          this.count = data.data.count;
          this.loading = false;
        });
    },
    listRoles() {
      this.axios.get("/api/role/getRoleList").then(({ data }) => {
        this.userRoleList = data.data;
      });
    }

  },
  watch: {
    loginType() {
      this.current = 1;
      this.listUsers();
    },
    keywords() {
      this.current = 1;
      this.listUsers();
    }
  }
};
</script>
