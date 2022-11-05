package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QQ用户信息Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QQUserInfoDTO {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * qq头像
     */
    private String figureurl_qq_1;
}
