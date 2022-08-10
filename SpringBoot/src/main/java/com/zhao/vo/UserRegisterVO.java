package com.zhao.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVO {

    private String username;

    private String password;

    private String email;

    private String code;
}
