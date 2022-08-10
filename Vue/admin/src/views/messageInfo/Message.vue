<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
              type="danger"
              size="small"
              icon="el-icon-delete"
              :disabled="messageIdList.length === 0"
              @click="deleteFlag = true"
      >
        批量删除
      </el-button>
      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
                v-model="keywords"
                prefix-icon="el-icon-search"
                size="small"
                placeholder="请输入用户昵称"
                style="width:200px"
                @keyup.enter.native="listMessages"
        />
        <el-button
                type="primary"
                size="small"
                icon="el-icon-search"
                style="margin-left:1rem"
                @click="listMessages"
        >
          搜索
        </el-button>

        <el-button
            size="warning"
            icon="el-icon-refresh"
            @click="rest"
        >
          重置
        </el-button>
      </div>
    </div>
    <!-- 表格展示 -->
    <el-table border :data="messageList" @selection-change="selectionChange">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="avatar" label="头像" align="center" width="150">
        <template slot-scope="scope">
          <img :src="scope.row.avatar" width="40" height="40" />
        </template>
      </el-table-column>
      <el-table-column
              prop="nickname"
              label="留言人"
              align="center"
              width="150"
      />
      <el-table-column prop="messageContent" label="留言内容" align="center" />
      <el-table-column
              prop="ipAddress"
              label="ip地址"
              align="center"
              width="150"
      />
      <el-table-column
              prop="ipSource"
              label="ip来源"
              align="center"
              width="170"
      />
      <el-table-column
              prop="createTime"
              label="留言时间"
              width="140"
              align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" width="100" align="center">
        <template slot-scope="scope">
          <el-popconfirm
                  title="确定删除吗？"
                  @onConfirm="deleteMessage(scope.row.id)"
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
            class="pagination-container"
            background
            @size-change="sizeChange"
            @current-change="currentChange"
            :current-page="current"
            :page-size="size"
            :total="count"
            :page-sizes="[5, 10, 20]"
            layout="total, sizes, prev, pager, next, jumper"
    />

    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="deleteFlag" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="deleteFlag = false">取 消</el-button>
        <el-button type="primary" @click="deleteMessage(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>

  </el-card>
</template>

<script>
  import {listMessages, deleteMessage} from '@/api/message';
  import {deleteComments} from "@/api/comment";
  export default {
    created() {
      this.listMessages();
    },
    data: function() {
      return {
        deleteFlag: false,
        messageIdList: [],
        messageList: [],
        keywords: '',
        current: 1,
        size: 5,
        count: 0
      };
    },
    methods: {
      selectionChange(messageList) {
        this.messageIdList = [];
        messageList.forEach(item => {
          this.messageIdList.push(item.id);
        });
        // console.log(this.messageIdList)
      },
      sizeChange(size) {
        this.size = size;
        this.listMessages();
      },
      currentChange(current) {
        this.current = current;
        this.listMessages();
      },
      async listMessages() {
        const res = await listMessages(this.current, this.size, this.keywords)
        const cons = res.data;
        if (cons.flag) {
          this.messageList = cons.data.messageList;
          this.count = cons.data.count;
        }
      },
      rest() {
        this.keywords = '';
        this.listMessages();
      },
      async deleteMessage(id) {
        let param = [];
        if (id == null) {
          param = this.messageIdList;
        } else {
          param = [id];
        }
        // console.log(param)
        const res = await deleteMessage(param);
        const cons = res.data
        // console.log(cons)
        if (cons.flag) {
          this.$notify.success({
            title: "成功",
            message: cons.message
          });
          this.listMessages();
        } else {
          this.$notify.error({
            title: "失败",
            message: cons.message
          });
        }
        this.deleteFlag = false;
      },
    },
    watch: {
      isDelete() {
        this.listMessages();
      }
    }
  };
</script>

<style>
  .operation-container {
    display: flex;
    align-items: center;
    margin-bottom: 1.25rem;
  }
</style>
