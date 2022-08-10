import request from "../utils/request"


/**
 *
 * @param current
 * @param size
 * @param nickname
 * @returns {AxiosPromise}
 */
export const listLinks=(current, size, nickname) =>{
    return request({
        url: "/friendLink/listLinks",
        method: "get",
        params: {
            current,
            size,
            nickname
        }
    })
}

/**
 *
 * @param data
 * @returns {AxiosPromise}
 */
export const addOrEditCategory = (data) => {
    return request({
        url:"/friendLink/addOrEditFriendLink",
        method: "post",
        data : data
    })
}