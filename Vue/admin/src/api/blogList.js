import request from "../utils/request"

/**
 * 查询博客列表
 * @param pageNum
 * @param pageSize
 * @param articleTitle
 * @returns {AxiosPromise}
 */
export  const listArticle=(pageNum,pageSize,articleTitle)=>{
  return request({
    url: "/article/listArticle",
    method: "get",
    params:{
      pageNum,
      pageSize,
      articleTitle,
    }
  })
}

/**
 * 删除博客
 * @param articleId
 * @returns {AxiosPromise}
 */
export  const deleteArticleById=(articleId)=>{
  return request({
    url: "/article/deleteArticleById",
    method: "delete",
    params:{
      articleId
    }
  })
}

/**
 * 批量删除博客
 * @param ids
 * @returns {AxiosPromise}
 */
export const deleteBatchById=(ids)=>{
  return request({
    url: "/article/del/batch",
    method:"post",
    data:ids
  })
}
