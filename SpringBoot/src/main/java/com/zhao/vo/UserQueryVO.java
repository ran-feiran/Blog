package com.zhao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer startIndex; // 起始位置（startIndex-1）*pageSize
    private Integer pageSize; // 页面大小
    private String roleName;
    private String nickname;
}
