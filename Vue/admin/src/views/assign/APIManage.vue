<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}
      <span v-if="actionId === '1'">
        <el-tag type="success" size="big">
          {{ roleName }}
        </el-tag>
      </span>
      <span v-else-if="actionId === '2'">
        <el-tag type="warning" size="big">
          {{ roleName }}
        </el-tag>
      </span>
      <span v-else-if="actionId === '3'">
        <el-tag type="primary" size="big">
          {{ roleName }}
        </el-tag>
      </span>
      <span v-else>
        <el-tag type="info" size="big">
          {{ roleName }}
        </el-tag>
      </span>
      <el-tag v-if="apiIdList1.length != 0" type="warning" size="big" style="position: relative;right: -5px" >
        已分配API_ID ：{{ apiIdList1 }}
      </el-tag>
    </div>
    <!-- 表格操作 -->
    <div class="operation-container" style="position: relative;bottom: -15px">
      <el-button
          plain
              type="success"
              size="small"
              icon="el-icon-position"
          @click="save"

      >
        保存分配设置
      </el-button>
      <el-button
          plain
              type="primary"
              size="small"
              icon="el-icon-refresh"
              @click="getApiInfoFromSwagger"
      >
        同步Swagger2
      </el-button>
      <div style="margin-left:auto">
        <el-input
                v-model="apiId"
                prefix-icon="el-icon-search"
                size="small"
                placeholder="请输入api_id(1~15)"
                style="width:200px"
        />
        <el-button
            plain
                type="primary"
                size="small"
                icon="el-icon-search"
                style="margin-left:1rem"
                @click="getApiListInfo"
        >
          搜索
        </el-button>
        <el-button
            plain
            type="warning"
            size="small"
            icon="el-icon-refresh"
            @click="reset"
        >
          重置
        </el-button>
      </div>
    </div>

  <!--展示用户表格    y-->
  <el-table
          :data="apiListInfo"
          border stripe
          max-height="500px"
          row-key="id"
          style="width: 100%;position: relative;bottom: -10px;"
          >
    <el-table-column
            prop=""
            label="分配情况"
            width="120px"
            align="center"
    >
      <template slot-scope="scope" >
        <span v-if="apiIdList.includes(scope.row.id)">
          <el-tag size="small" type="success" style="position: relative;left: -15px">
            已分配
          </el-tag>
        </span>
      </template>
    </el-table-column>
    <!--昵称      n-->
    <el-table-column
            prop="name"
            label="API名称"
            width="400px"
            align="center"
    >
      <template slot-scope="scope" >
        <el-tag v-if="scope.row.pid==null" size="small" type="success">
          {{ scope.row.name }}
        </el-tag>
        <span v-else>
          <el-tag size="mini" type="primary">
          {{ scope.row.name }}
          </el-tag>
        </span>
      </template>
    </el-table-column>

    
    <el-table-column
            prop="url"
            label="请求地址"
            width="100px"
            align="center"
    >
    </el-table-column>
    <!--ip来源-->
    <el-table-column
            prop="method"
            label="请求方式"
            width="100px"
            align="center"
    >
      <template slot-scope="scope" >
        <el-tag v-if="scope.row.method">
          {{ scope.row.method }}
        </el-tag>
      </template>
    </el-table-column>


    <el-table-column
            prop="description"
            label="描述"
            align="center"
    >
    </el-table-column>

    <el-table-column :aria-checked="true" width="40" prop="id"  label="ID" align="center">
    </el-table-column>
    <!-- 列操作 -->
    <el-table-column label="操作" align="center" width="160">
      <template slot-scope="scope">
        <el-button @click="handleSelectionChange(scope.row.id)" v-if="scope.row.pid != null || scope.row.apiId === 15" type="success" plain>
          添加
        </el-button>
        <el-button @click="handleCancelChange(scope.row.id)" v-if="scope.row.pid != null || scope.row.apiId === 15" type="primary" plain>
          取消
        </el-button>
      </template>
    </el-table-column>

  </el-table>
    
  </el-card>
</template>

<script>
import {getApiInfoFromSwagger, getAssignedApiIdByUserRoleId, listApiInfoBack, saveOrUpdateApiFromSwagger,
  saveRolePermissionList } from "@/api/swaggerApi";
  export default {
    name: "APIManage",
    data() {
      return {

        actionId:null,
        roleName: '',
        //数据库获取的api列表信息
        apiListInfo:[],
        apiId:null,
        //选中角色所拥有的api权限id列表
        apiIdList:[],
        apiIdList1:[], // 备用
        rolePermissionList:[],

        //从swagger文档中获取数据存储
        apiList: [{
          id:0,
          apiId: null,
          name: "",
          url: "",
          method: "",
          pid: "",
          description: "",
        },],
      }
    },
    created() {
      this.apiIdList1 = []
      const path = this.$route.path;
      const arr = path.split("/");  //分割取得后面的id
      const roleId = arr[2];
      this.actionId=roleId;

      this.axios.get('/api/role/getRoleNameById',  {
        params: {
          roleId: this.actionId
        },
      }).then(res =>{
        const cons = res.data;
        if (cons.flag) {
          this.roleName = cons.data.roleName
        }
      })

      this.getApiListInfo(); // 查询所有api
      console.log(11111111)
      this.getAssignedApiIdByUserRoleId(); // 通过角色查询对应得api
    },
    methods: {
      async save(){
        // console.log(this.apiIdList1)
        const res = await saveRolePermissionList(this.actionId, this.apiIdList1)
        const cons = res.data;
        if (cons.flag) {
          this.$message.success(cons.message)
        } else {
          this.$message.error(cons.message)
        }
        this.getAssignedApiIdByUserRoleId();
      },
      handleSelectionChange(id){
        if (!this.apiIdList1.includes(id)) {
          this.apiIdList1.push(id)
        } else {
          this.$message.error("已添加")
        }
        // console.log(this.apiIdList1)
      },
      handleCancelChange(id) {
        this.$message.success("API_ID ："+id+" 已取消")
        let newId = this.apiIdList1.findIndex(item => item === id)
        this.apiIdList1.splice(newId, 1)
        // console.log(this.apiIdList1)
      },
      async  getAssignedApiIdByUserRoleId(){
        const res = await getAssignedApiIdByUserRoleId(this.actionId);
        const cons = res.data
        if(cons.flag){
          this.apiIdList=cons.data.apiIdList;
          // console.log(this.apiIdList)
        } else {
          this.$message.error(cons.message)
        }
      },
      //从数据库获取api信息
      async getApiListInfo(){
        const res =  await listApiInfoBack(this.apiId);
        const cons = res.data;
        if(cons.flag){
          this.apiListInfo=cons.data.apiListInfo;
          // this.count = cons.data.count;
          // console.log(this.apiListInfo)
        } else {
          this.$message.error(cons.message)
        }
      },
      reset() {
        this.apiId = '';
        this.getApiListInfo()
      },




























      async getApiInfoFromSwagger() {
        const {data} = await getApiInfoFromSwagger();
        console.log(data);
        var x=1;
        //清空原来的数据 //重新获取
        this.apiList=[];
        for (var i in data.tags) {
          // i就是tags数组的下标//用这个下标来组成父的主键id值
          var m=  parseInt(i)+1;
          var name = data.tags[i].name;
          var parent = {
            id:x,
            apiId: m,
            name: name+"",
            description: data.tags[i].description+"",
          }
          x=x+1;
          //尿呀  这样就把父id搞定了 搞定一个父在搞定父下面的子
          this.apiList.push(parent);
          for(var j in data.paths){
            //j就是子的url
            //判断当前是不是父的子
            for(var k in data.paths[j]){
              //k就是他的请求方//在拿到他的父api名称
               var pidName= data.paths[j][k].tags
              if(pidName!=name){
                //跳出当前循环
                 break;
              } else {
                var children={
                  id:x,
                  apiId:null,
                  name: data.paths[j][k].summary+"" ,
                  url:  j,
                  method:k,
                  pid:m ,
                  description:  data.paths[j][k].summary+""
                }
                this.apiList.push(children);
                x=x+1;
              }
            }
          }
        }
        console.log(this.apiList);
        this.saveOrUpdateApiFromSwagger();
      },
      async saveOrUpdateApiFromSwagger(){
       const {data} = await saveOrUpdateApiFromSwagger(this.apiList);
         if(data.success){
           this.$message.success("更新swaggerApi信息到数据库成功") ;
         }else {
           this.$message.error("更新swaggerApi信息到数据库失败") ;
         }
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
</style>
