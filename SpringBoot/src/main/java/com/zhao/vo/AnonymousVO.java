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
public class AnonymousVO {

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotNull(message = "匿名状态不能为空")
    private Integer isAnonymous;
}
