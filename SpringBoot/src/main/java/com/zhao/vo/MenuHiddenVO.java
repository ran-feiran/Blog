package com.zhao.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "菜单隐藏")
public class MenuHiddenVO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Integer id;

    /**
     * 置顶状态
     */
    @NotNull(message = "隐藏状态不能为空")
    private Integer isHidden;
}
