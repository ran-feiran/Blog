package com.zhao.exception;


import com.zhao.result.ResultStandby;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.zhao.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.zhao.result.ResultStandby.error;


/**
 * 全局异常处理
 *
 * @author ran-feiran
 * @date 2022/10/07
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * 处理服务异常
     *
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    public ResultStandby<?> handle(ServiceException se){
        return error(se.getCode(), se.getMessage());
    }


    /**
     * 处理系统异常
     *
     * @param se 异常
     * @return 接口异常信息
     */
    @ExceptionHandler(Exception.class)
    public  ResultStandby<?> handleAll(Exception se){
        se.printStackTrace();
        return error(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getDesc());
    }
}
