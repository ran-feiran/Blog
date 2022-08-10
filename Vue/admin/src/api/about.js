import request from '../utils/request'


/**
 * 获得关于我的信息
 * @returns {AxiosPromise}
 */
export const getAboutMe = () => {
    return request({
        url: "/getAbout",
        method: "get"
    })
}

/**
 * 更新关于我
 * @param data
 * @returns {AxiosPromise}
 */
export const updateAboutMe = (data) => {
    return request({
        url: "/updateAbout",
        method: "post",
        data : data,
    })
}