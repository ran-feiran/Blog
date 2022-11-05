package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 文章搜索dto
 *
 * @author ran-feiran
 * @date 2022/09/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 文章内容
     */
    private String articleContent;

    /**
     * 文章状态
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer isDelete;

}
