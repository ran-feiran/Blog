import request from "../utils/request"
/**
 * 后面的每次请求都是需要携带token
 * @returns {AxiosPromise}
 */
//查询用户列表
export  const findUserList= (userQueryVO) =>{
  return request({
    url: "/user/getUserList",
    method:"get",
    params:{
      startIndex: userQueryVO.startIndex,
      pageSize: userQueryVO.pageSize,
      roleName: userQueryVO.roleName,
      nickname: userQueryVO.nickname,
    }
  })
}

// 查询角色列表
export  const fndRoleList = () =>{
  return request({
    url: "/role/getRoleList",
    method:"get",
  })
}

//通过用户id，修改用户的部分字段 这里是用户头像和用户昵称
export  const updateUserInfo=(data)=>{
  return request({
    url: "/user/updateUserById",
    method: "put",
    data:data,
  })
}

//增加用户
export  const addUser=(data)=>{
  return request({
    url: "/user/addUser",
    method: "post",
    data:data,

  })
}

//通过id修改用户禁言状态
export  const updateSilenceById=(flag,id)=>{
  return request({
    url: "/user/updateSilenceById",
    method: "put",
    params: {
      id,
      flag,
    }
  })
}

//通过用户id 逻辑删除该用户
export  const logicDeleteUserById=(id)=>{
  return request({
    url: "/user/logicDeleteUser",
    method: "delete",
    params:{
      id,
    }
  })
}

//查询用户角色
export  const getUserList=(current,size,nickname)=>{
  return request({
    url: "/user/getUserListSignal",
    method: "get",
    params:{
      current,size,nickname
    },
  })
}

//修改用户角色
export const updateUserRole = (data) =>{
  return request({
    url: "/user/updateUserRole",
    method : "post",
    data:data,
  })
}