package com.zhao.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 *
 * @author ran-feiran
 * @date 2022/10/16
 */
@Getter
@Setter
@AllArgsConstructor
public class ServiceException extends RuntimeException{

    private String code;

    private String message;
}
