package com.zhao.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageVO {

    private Integer id;

    @NotNull(message = "页面标签不能为空")
    private String pageLabel;

    @NotNull(message = "页面名称不能为空")
    private String pageName;

    @NotNull(message = "页面封面不能为空")
    private String pageCover;

}
