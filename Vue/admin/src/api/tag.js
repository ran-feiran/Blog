import request from "../utils/request"

//获取标签列表
export  const listTags=(pageNum,pageSize,tagName)=>{
  return request({
    url: "/tag/listTags",
    method: "get",
    params:{
      pageNum,
      pageSize,
      tagName
    }
  })
}


export  const addOrEditTag=(data)=>{
  return request({
    url: "/tag/addOrEditTag",
    method: "post",
    data:data
  })
}

export  const deleteTag=(tagId)=>{
  return request({
    url: "/tag/deleteTag",
    method: "delete",
    params: {
      tagId
    }
  })
}

export const delBatch=(ids) =>{
  return request({
    url: "/tag/del/batch",
    method:"post",
    data:ids
  })
}