package com.zhao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArticleBlogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private String articleTitle;

    private String articleCover;

    private String articleContent;

    private Integer categoryId;

    private String categoryName;

    private List<TagDTO> tagList;

    private Integer isTop;

    private Date createTime;

    private Date updateTime;

    private String nickname;

    /**
     * 访问量
     */
    private Integer viewsCount;

    /**
     * 点赞
     */
    private Integer likeCount;

    /**
     * 上一篇文章
     */
    private ArticlePaginationDTO lastArticle;

    /**
     * 下一篇文章
     */
    private ArticlePaginationDTO nextArticle;

    /**
     * 推荐文章列表
     */
    private List<ArticleRecommendDTO> recommendArticleList;

    /**
     * 最新文章列表
     */
    private List<ArticleRecommendDTO> newestArticleList;


}
