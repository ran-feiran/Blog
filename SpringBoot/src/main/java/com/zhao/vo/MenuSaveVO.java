package com.zhao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuSaveVO {

    private Integer id;

    private String name;

    private String path;

    private String component;

    private String icon;

    private Date createTime;

    private Date updateTime;

    private Integer orderNum;

    private Integer parentId;

    private Integer isHidden;
}
