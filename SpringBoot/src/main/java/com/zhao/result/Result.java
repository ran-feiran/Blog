package com.zhao.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * 定制统一结果返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result {

    /**
     * 返回状态 true false
     */
    private boolean flag;

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回数据
     */
    private Map<String,Object> data = new HashMap<>();

    /**
     * 返回信息
     */
    private  String message;

    public static Result success() {
        return new Result(true, ResultInfo.CODE_200, null, "操作成功");
    }

    public static Result success(Map<String,Object> data, String msg) {
        return new Result(true, ResultInfo.CODE_200, data, msg);
    }

    public static Result error() {
        return new Result(false, ResultInfo.CODE_500, null, "系统错误");
    }

    public static Result error(String code, String msg) {
        return new Result(false, code, null, msg);
    }

}
