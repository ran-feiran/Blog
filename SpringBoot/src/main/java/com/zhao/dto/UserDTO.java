package com.zhao.dto;


import com.zhao.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Integer userId;

    private String nickname;

    private String avatar;

    private Integer loginType;

    private List<Role> roleList;

    private Integer isSilence;

    private String ipAddress;

    private String ipSource;

    private Date createTime;

    private Date lastLoginTime;
}
