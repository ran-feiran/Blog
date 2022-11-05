package com.zhao.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "修改密码")
public class PasswordVO {

    @NotNull(message = "旧密码不能为空")
    private String newPassword;

    @NotNull(message = "新密码不能为空")
    private String oldPassword;

    @NotNull(message = "确认密码不能为空")
    private String confirmPassword;
}
