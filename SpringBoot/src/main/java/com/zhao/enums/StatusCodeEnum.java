package com.zhao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {
    /**
     * 成功
     */
    SUCCESS("200", "操作成功"),
    /**
     * 未登录
     */
    NO_LOGIN("400", "用户未登录"),
    /**
     * 没有操作权限
     */
    AUTHORIZED("403", "权限不足"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR("500", "系统异常"),
    /**
     * 失败
     */
    FAIL("510", "操作失败"),
    /**
     * 参数校验失败
     */
    VALID_ERROR("520", "参数格式不正确"),
    /**
     * 用户名已存在
     */
    USERNAME_EXIST("521", "用户名已存在"),
    /**
     * 用户名不存在
     */
    USERNAME_NOT_EXIST("522", "用户名不存在"),
    /**
     * qq登录错误
     */
    QQ_LOGIN_ERROR("530", "qq登录错误"),
    /**
     * 上传错误
     */
    UPLOAD_ERROR("535","文件上传失败"),
    /**
     * 用户名密码错误
     */
    USERNAME_PASSWORD_ERROR("540","账号或密码错误");

    /**
     * 状态码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
}
