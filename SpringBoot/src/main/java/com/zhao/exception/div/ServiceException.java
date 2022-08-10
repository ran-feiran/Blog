package com.zhao.exception.div;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceException extends RuntimeException{

    private String code;
    private String message;
}
