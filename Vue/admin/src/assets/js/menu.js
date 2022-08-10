import router from "../../router";
import store from "../../store";
import Vue from "vue";
import Layout from '@/components/Main'
import { getMenuList } from '@/api/menu'

export function generaMenu() {
    getMenuList(store.state.userRoles).then(res => {
        const cons = res.data
        if (cons.flag) {
            let menuList = cons.data.menuList
            let userMenuList = [ {path:"/main", component: loadHomeView(), children:[]} ]
            menuList.forEach(item => {

                if (item.menuIcon != null) {
                    item.menuIcon = ""+ item.menuIcon;
                }

                if (item.menuUrl) {
                   userMenuList[0].children.push({
                       path: item.menuUrl,
                       name: item.menuName,
                       component: loadView(item.menuComponent)
                   })
                }

                if (item.children && item.children.length > 0) {
                    item.children.forEach(route => {
                        route.menuIcon = "" + route.menuIcon;
                        userMenuList[0].children.push({
                            path: route.menuUrl,
                            name: route.menuName,
                            component: loadView(route.menuComponent)
                        });

                        if (route.menuUrl === '/assignAPI') {
                            userMenuList[0].children.push({
                                path:"/swaggerApi/*",
                                name:"接口管理",
                                component:loadView('/assign/APIManage')
                            });
                        }

                        if (route.menuUrl === '/blogList') {
                            userMenuList[0].children.push({
                                path:"/article/*",
                                name:"修改文章",
                                component:loadView('/blog/BlogAdd')
                            });
                        }
                    })
                }
            });
            // console.log(12222222111)
            // console.log(userMenuList)
            // console.log(115555551111)
            // 添加侧边栏菜单
            store.commit("menuList", menuList)
            // 添加菜单到路由
            router.addRoutes(userMenuList);
        } else {
            Vue.prototype.$message.error(cons.message);
            router.push({ path: "/login" });
        }
    })

}

// 路由懒加载
export const loadView = ( view ) => {
    return () => import('@/views'+view)
};

export const loadHomeView = (  ) => {
    return () => import('@/components/Main')
};