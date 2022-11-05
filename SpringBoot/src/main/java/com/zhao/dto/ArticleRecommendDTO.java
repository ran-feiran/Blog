package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 推荐文章
 *
 * @author ran-feiran
 * @date 2022/09/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleRecommendDTO {

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

    /**
     * 创建时间
     */
    private Date createTime;
}
