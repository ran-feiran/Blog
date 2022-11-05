package com.zhao.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import static com.zhao.enums.StatusCodeEnum.*;

/**
 * 统一结果返回类备用类
 *
 * @author ran-feiran
 * @date 2022/10/12
 */
@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class ResultStandby<T> {

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
    private T data;

    /**
     * 返回信息
     */
    private  String message;

    public static<T> ResultStandby<T> success() {
        return create(true, SUCCESS.getCode(), null, SUCCESS.getDesc());
    }

    public static<T> ResultStandby<T> success(T data, String msg) {
        return create(true, SUCCESS.getCode(), data, msg);
    }

    public static<T> ResultStandby<T> error() {
        return create(false, FAIL.getCode(), null, FAIL.getDesc());
    }

    public static<T> ResultStandby<T> error(String code, String msg) {
        return create(false, code, null, msg);
    }
}
