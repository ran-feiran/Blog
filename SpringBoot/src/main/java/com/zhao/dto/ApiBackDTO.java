package com.zhao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ApiBackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Date createTime;

    private List<ApiBackDTO> children = new ArrayList<>();

}
