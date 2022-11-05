package com.zhao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_resource")
@ApiModel(value="Api对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class Api implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "//资源名")
    private String resourceName;

    @ApiModelProperty(value = "//资源路径")
    private String url;

    @ApiModelProperty(value = "//请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "//父id")
    private Integer parentId;

    @ApiModelProperty("//是否匿名访问")
    private Integer isAnonymous;

    @ApiModelProperty(value = "//创造时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "//更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}