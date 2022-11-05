package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {

    private Integer userLoginId;

    private String nickname;

    private String ipAddress;

    private String ipSources;

    private Date lastLoginTime;

    private String avatar;

    private String os;

    private String browser;
}
