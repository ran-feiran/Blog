import request from '../utils/request';

/**
 * 分页查询留言
 * @param current
 * @param size
 * @param nickname
 * @returns {AxiosPromise}
 */
export const listMessages= (current, size, nickname) => {
    return request({
        url: "/message/getMessageList",
        method: "get",
        params: {
            current,size,nickname
        }
    })
}

/**
 * 批量删除留言
 * @param ids
 * @returns {AxiosPromise}
 */
export const deleteMessage = (ids) => {
    return request({
        url: "/message/deleteMessages",
        method: "post",
        data: ids
    })

}
