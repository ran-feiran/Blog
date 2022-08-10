import request from "../utils/request"


//后台用户登录
//这里指定使用login 后台使用了spring security框架  或者后台去设置一下 其他的login请求路径
export  const getMenuList=(roleName)=>{
  return request({
    url: "/menu/getMenuList",
    method: "get",
    params:{
      roleName
    }
  })
}


// 查询所有菜单
export const getAllMenuList = () =>{
  return request({
    url: "/menu/getAllMenuList",
    method: "get",
  })
}


// 更新角色菜单
export const saveRoleMenu = (roleId,data) =>{
  return request({
    url: "/role/saveRoleMenu/"+roleId,
    method: "post",
    data: data
  })
}