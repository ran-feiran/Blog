package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 标签后台返回
 *
 * @author ran-feiran
 * @date 2022/10/05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagBackDTO {
    /**
     * 标签id
     */
    private Integer tagId;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 文章量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    private Date createTime;

}