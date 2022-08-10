<template >
<!--  <div  style="overflow: hidden;">-->
<!--    <el-menu-->
<!--            default-active="1"-->
<!--            class="el-menu-sidebar"-->
<!--            background-color="#304156"-->
<!--            text-color="#BFCBD9"-->
<!--            active-text-color="#ffd04b"-->
<!--            router-->
<!--            collapse-transition-->
<!--            :collapse="this.$store.state.collapse"-->
<!--            :unique-opened="true">-->
<!--      <menu-tree :menuList="this.menuList"></menu-tree>-->
<!--    </el-menu>-->
<!--  </div>-->
  <el-scrollbar style="height:100%;overflow-x: hidden;">
    <el-menu
        :unique-opened="true"
        class="side-nav-bar"
        router
        :collapse="$store.state.collapse"
        :default-active="$route.path"
        background-color="#304156"
        text-color="#BFCBD9"
        active-text-color="#ffd04b"
    >
      <menu-tree :menuList="$store.state.menuList"></menu-tree>
    </el-menu>
  </el-scrollbar>
</template>

<script>
  import MenuTree from "./MenuTree";
  import {getMenuList} from "@/api/menu";
  export default {
    data(){
      return{
        //菜单列表
        menuList:[],
      }
    },
    created() {
      //获取展示菜单
      //判断缓存里面有没有菜单列表 有的话就直接赋值
      // 没有的话重新请求
      //  let menuList1= this.$store.state.menuList;
      //  if( menuList1.length > 0){
      //    this.menuList=menuList1;
      //    console.log(1)
      //  }else {
      //    this.getMenuList();
         // console.log(this.menuList)
       //   console.log(2)
       // }

    } ,
    name: "SidBar" ,
    components:{
      MenuTree,
    },
    methods:{

      //获取展示菜单列表
      async getMenuList(){
        // console.log(this.$store.state.userRoles)
        // console.log(this.$store.state.userRoles)
        const res = await getMenuList(this.$store.state.userRoles);
        const cons = res.data;
        // console.log(res)
        if(cons.flag){
          this.menuList=cons.data.menuList;
          console.log(this.menuList)
          this.$store.commit("menuList", cons.data.menuList);
          // console.log(this.$store.state.menuList)
          this.$message.success(cons.message);
          //然后把菜单数据放到vuex里面 避免每次刷新都tm要去加载
          //vuex的数据持久是依赖session的 所以清除缓存 vuex的数据就没了
        }else{
          this.$message.error(cons.message);
          // console.log(3)
        }
      }
    },
    computed:{
      // isFold(){
      //   return this.$store.state.collapse ? "el-icon-s-unfold" : "el-icon-s-fold";
      // },
    }
  }
</script>

<style scoped>
.side-nav-bar:not(.el-menu--collapse) {
  width: 210px;
}
.side-nav-bar {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
}
.side-nav-bar i {
  margin-right: 1rem;
}
</style>
