package com.zhao.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVO {

    private String username;

    private String password;

    @NotNull(message = "邮箱不能为空")
    private String email;

    @NotNull(message = "验证码不能为空")
    private String code;
}
