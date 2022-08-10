
import request from "../utils/request"


export  const listCategory=(pageNum,pageSize,categoryName)=>{
  return request({
    url: "/category/listCategory",
    method: "get",
    params:{
      pageNum,
      pageSize,
      categoryName
    }
  })
}

export  const addOrEditCategory=(data)=>{
  return request({
    url: "/category/addOrEditCategory",
    method: "post",
    data:data
  })
}

export  const deleteCategory=(categoryId)=>{
  return request({
    url: "/category/deleteCategory",
    method: "delete",
    params: {
    categoryId
    }
  })
}


export const deleteBatchById=(ids)=>{
  return request({
    url: "/category/del/batch",
    method:"post",
    data:ids
  })
}
