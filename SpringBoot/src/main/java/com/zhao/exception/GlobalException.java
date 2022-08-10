package com.zhao.exception;


import com.zhao.result.Result;
import com.zhao.exception.div.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalException {

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(), se.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleAll(Exception se){
        return Result.error();
    }
}
