import request from "@/utils/request";

export const getHomeInfo = () =>{
    return request({
        url: "/home/getHomeInfo",
        method: "get",
    })
}