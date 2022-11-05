package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签数据统计
 *
 * @author ran-feiran
 * @date 2022/10/03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    /**
     * id
     */
    private Integer tagId;

    /**
     * 标签名
     */
    private String tagName;

}
