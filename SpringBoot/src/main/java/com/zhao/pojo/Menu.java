package com.zhao.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("tb_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer menuId;

    private String menuName;

    private String menuUrl;

    private String menuComponent;

    private String menuIcon;

    private Integer menuSort;

    private Integer parentId;

    private String description;
}
