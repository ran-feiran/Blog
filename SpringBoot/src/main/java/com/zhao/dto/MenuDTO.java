package com.zhao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

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

    private Integer isDelete;

    private List<MenuDTO> children = new ArrayList<>();
}
