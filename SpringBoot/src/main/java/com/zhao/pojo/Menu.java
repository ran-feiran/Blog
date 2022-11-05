package com.zhao.pojo;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@TableName("tb_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String path;

    private String component;

    private String icon;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("排序数目")
    private Integer orderNum;

    private Integer parentId;

    @ApiModelProperty("是否隐藏")
    private Integer isHidden;

    @ApiModelProperty("是否删除")
    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;
}
