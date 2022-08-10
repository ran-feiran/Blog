import request from "../utils/request"

/**
 * 分页查询用户评论
 * @param pageNum
 * @param pageSize
 * @param nickname
 * @returns {AxiosPromise}
 */
export const getUserCommentList=(pageNum, pageSize, nickname) =>{
    return request({
        url: "/comment/getUserCommentList",
        method: "get",
        params: {
            pageNum,
            pageSize,
            nickname
        }
    })
}


/**
 * 删除用户评论
 * @param ids
 * @returns {AxiosPromise}
 */
export const deleteComments = (ids) =>{
    return request({
        url: "/comment/delComments",
        method: "post",
        data: ids
    })
}