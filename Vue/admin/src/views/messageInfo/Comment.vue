<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 表格操作 -->
    <div class="operation-container">
      <el-button
              v-if="isDelete === 0"
              type="danger"
              size="small"
              icon="el-icon-delete"
              :disabled="commentIdList.length === 0"
              @click="remove = true"
      >
        批量删除
      </el-button>
      <el-button
              v-else
              type="danger"
              size="small"
              icon="el-icon-deleteItem"
              :disabled="commentIdList.length === 0"
              @click="remove = true"
      >
        批量删除
      </el-button>
      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
                v-model="nickname"
                prefix-icon="el-icon-search"
                size="small"
                placeholder="请输入用户昵称"
                style="width:200px"
                @keyup.enter.native="listComments"
        />
        <el-button
                type="primary"
                size="small"
                icon="el-icon-search"
                style="margin-left:1rem"
                @click="listComments"
        >
          搜索
        </el-button>
        <el-button
            type="warning"
            size="small"
            icon="el-icon-refresh"
            @click="rest"
        >
          重置
        </el-button>
      </div>

    </div>
    <!-- 表格展示 -->
    <el-table border :data="commentList" @selection-change="selectionChange">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="avatar" label="头像" align="center" width="120">
        <template slot-scope="scope">
          <el-image :src="scope.row.avatar" width="40" height="40" />
        </template>
      </el-table-column>
      <!-- 评论人昵称 -->
      <el-table-column
              prop="nickname"
              label="评论人"
              align="center"
              width="120"
      />
      <!-- 回复人昵称 -->
      <el-table-column
              prop="replyNickname"
              label="回复人"
              align="center"
              width="120"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.replyNickname">
            {{ scope.row.replyNickname }}
          </span>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <!-- 评论文章标题 -->
      <el-table-column prop="articleTitle" label="文章标题" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.articleTitle">
            {{ scope.row.articleTitle }}
          </span>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <!-- 评论内容 -->
      <el-table-column prop="commentContent" label="评论内容" align="center">
        <template slot-scope="scope">
          <span v-html="scope.row.commentContent" class="comment-content" />
        </template>
      </el-table-column>

      <!-- 点赞量 -->
      <el-table-column
              prop="likeCount"
              label="点赞量"
              width="80"
              align="center"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.likeCount">
            {{ scope.row.likeCount }}
          </span>
          <span v-else>0</span>
        </template>
      </el-table-column>

      <!-- 评论时间 -->
      <el-table-column
              prop="createTime"
              label="评论时间"
              width="150"
              align="center"
      >
        <template slot-scope="scope">
          <i class="el-icon-time" style="margin-right:5px" />
          {{ scope.row.createTime | date }}
        </template>
      </el-table-column>
      <el-table-column label="来源" align="center" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.articleTitle" size="big">文章</el-tag>
          <el-tag v-else type="warning" size="big">友链</el-tag>
        </template>
      </el-table-column>

      <!-- 列操作 -->
      <el-table-column label="操作" width="160" align="center">
        <template slot-scope="scope">
          <el-popconfirm
                  title="确定删除吗？"
                  @onConfirm="deleteComments(scope.row.id)"
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
            :current-page="pageNum"
            :page-size="pageSize"
            :total="total"
            :page-sizes="[3,6,10]"
            layout="total, sizes, prev, pager, next, jumper"
    />



    <!-- 批量彻底删除对话框 -->
    <el-dialog :visible.sync="remove" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否彻底删除选中项？</div>
      <div slot="footer">
        <el-button @click="remove = false">取 消</el-button>
        <el-button type="primary" @click="deleteComments(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>

  </el-card>
</template>

<script>
import {getUserCommentList, deleteComments} from '@/api/comment'
   export default {
    created() {
      this.listComments();
    },
    data() {
      return {
        remove: false,
        updateIsDelete: false,
        commentList: [],
        commentIdList: [],
        nickname: "",
        isDelete: 0,
        pageNum: 1,
        pageSize: 3,
        total: 0
      };
    },
    methods: {
      rest() {
        this.nickname = ""
        this.listComments()
      },
      selectionChange(commentList) {
        this.commentIdList = [];
        commentList.forEach(item => {
          this.commentIdList.push(item.id);
        });
        // console.log(this.commentIdList)
      },
      sizeChange(val) {
        this.pageSize = val;
        this.listComments();
      },
      currentChange(val) {
        this.pageNum = val;
        this.listComments();
      },
      async listComments() {
        const res = await getUserCommentList(this.pageNum, this.pageSize, this.nickname)
        const cons = res.data
        // console.log(cons)
        if (cons.flag) {
           this.commentList = cons.data.userReplyList;
           this.total = cons.data.total;
          // this.$message.success(cons.message)
        } else {
          this.$message.error(cons.message)
        }
      },
      async deleteComments(id) {
        let param = [];
        if (id == null) {
          param = this.commentIdList;
        } else {
          param = [id];
        }
        // console.log(param)
        const res = await deleteComments(param);
        const cons = res.data
        // console.log(cons)
        if (cons.flag) {
          this.$notify.success({
            title: "成功",
            message: cons.message
          });
          this.listComments();
        } else {
          this.$notify.error({
            title: "失败",
            message: cons.message
          });
        }
        this.remove = false;
      },
    },
    watch: {
      isDelete() {
        this.listComments();
      }
    }
  };
</script>

<style scoped>
  .comment-content {
    display: inline-block;
  }
  .operation-container {
    display: flex;
    align-items: center;
    margin-bottom: 1.25rem;
  }
</style>
