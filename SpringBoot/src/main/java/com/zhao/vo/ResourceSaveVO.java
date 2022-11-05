package com.zhao.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceSaveVO {

    private Integer id;

    @NotNull(message = "资源名不能为空")
    private String resourceName;

    private String url;

    private String requestMethod;

    private Integer parentId;

    private Integer isAnonymous;

    private Date createTime;

    private Date updateTime;
}
