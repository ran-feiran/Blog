package com.zhao.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    private Integer userId;

    private String nickname;

    private String intro;

    private String webSite;

    private String email;
}
