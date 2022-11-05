package com.zhao.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

import static com.zhao.enums.StatusCodeEnum.*;
import static com.zhao.enums.StatusCodeEnum.FAIL;

/**
 *
 * 定制统一结果返回类
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
@Accessors(chain = true)
@Deprecated
public class Result {

    /**
     * 返回状态 true false
     */
    private Boolean flag;

    /**
     * 状态码
     */
    private String code;

    /**
     * 返回数据
     */
    private Map<String,Object> data;

    /**
     * 返回信息
     */
    private  String message;

    public static Result success() {
        return create(true, SUCCESS.getCode(), null, SUCCESS.getDesc());
    }

    public static Result success(Map<String,Object> data, String msg) {
        return create(true, SUCCESS.getCode(), data, msg);
    }

    public static Result error() {
        return create(false, FAIL.getCode(), null, FAIL.getDesc());
    }

    public static Result error(String code, String msg) {
        return create(false, code, null, msg);
    }

}
