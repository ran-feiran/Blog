package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignalDTO {

    private Integer userId;

    private String nickname;

    private String username;

    private String intro;

    private String avatar;

    private String roleName;

}
