package com.zhao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer userId;

    private String nickname;

    private String username;

    private String password;

    private String avatar;

    private String webSite;

    private Date createTime;

    private Date updateTime;

    private String roleName;

    private boolean isDelete;

    private boolean isSilence;
}
