import request from "../utils/request"

// 查询所有得api列表
export  const listApiInfoBack=(apiId)=>{
  return request({
    url: "/api/listApiInfoBack",
    method:"get",
    params:{
       apiId
    }
  })
}

// 查询角色列表
export  const getRoleList=()=>{
  return request({
    url: "/role/findRoleList",
    method:"get",
  })
}

// 添加或者更新角色信息
export const saveOrUpdateRole = (data) =>{
  return request({
    url: "/role/saveOrUpdateRole",
    method: "post",
    data: data
  })
}

// 删除角色
export const deleteRole = (id) =>{
  return request({
    url: "/role/deleteRole",
    method: "delete",
    params : {
      id
    }
  })
}

/**
 * 通过角色id查询角色得api id
 * @param actionId
 * @returns {AxiosPromise}
 */
export  const getAssignedApiIdByUserRoleId=(actionId)=>{
  return request({
    url: "/role/getAssignedApiIdByUserRoleId",
    method:"get",
    params:{
      actionId
    }
  })
}

/**
 * 保存角色api列表
 * @param roleId
 * @param apiIdList1
 * @returns {AxiosPromise}
 */
export const saveRolePermissionList=(roleId,apiIdList1)=>{
  return request({
    url: "/role/saveRolePermissionList/"+roleId,
    method:"post",
    data:apiIdList1
  })
}





















export  const getApiInfoFromSwagger=()=>{
  return request({
    url: "/v2/api-docs",
    method: "get"
  })
}

export const saveOrUpdateApiFromSwagger=(data)=>{
  return request({
    url: "/api/saveOrUpdateApiFromSwagger",
    method:"post",
    data:data
  })
}



