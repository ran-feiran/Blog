package com.zhao.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ApiVO {

    @TableId(value = "api_id", type = IdType.AUTO)
    private Integer apiId;

    @ApiModelProperty(value = "//api名")
    private String name;

    @ApiModelProperty(value = "//请求地址")
    private String url;

    @ApiModelProperty(value = "请求方式")
    private String method;

    @ApiModelProperty(value = "父id")
    private Integer pid;

    @ApiModelProperty(value = "描述")
    private String description;
}
