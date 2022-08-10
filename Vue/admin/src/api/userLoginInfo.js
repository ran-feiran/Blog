import request from "../utils/request"

//查询用户登录信息列表
export  const userLoginInfoList=(pageNum,pageSize,nickname)=>{
  return request({
    url: "/userLogin/getUserInfoList",
    method: "get",
    params:{
      pageNum,
      pageSize,
      nickname
    }
  })
}

export const deleteUser=(id)=>{
  return request({
    url:"/userLogin/deleteUser",
    method:"delete",
    params:{
      id
    }
  })
}