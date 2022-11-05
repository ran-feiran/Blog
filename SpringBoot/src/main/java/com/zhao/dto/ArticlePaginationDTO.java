package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 文章上下篇
 *
 * @author ran-feiran
 * @date 2022/09/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePaginationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer articleId;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;
}
