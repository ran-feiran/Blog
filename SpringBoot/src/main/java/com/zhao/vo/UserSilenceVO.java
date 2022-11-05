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
@ApiModel(description = "用户禁言")
public class UserSilenceVO {

    /**
     * id
     */
    @NotNull(message = "userId不能为空")
    private Integer userId;

    /**
     * 置顶状态
     */
    @NotNull(message = "禁言状态不能为空")
    private Integer isSilence;
}
