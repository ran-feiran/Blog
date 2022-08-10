package com.zhao.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.*;
import com.zhao.vo.ArticleAddVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article")
@ApiModel("文章表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Alias("articleId")
    @TableId(type = IdType.AUTO)
    private Integer articleId;

    @Alias("articleTitle")
    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("文章内容")
    private String articleContent; // 对应mysql中的longtext

    @ApiModelProperty("文章封面")
    private String articleCover;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("是否置顶")
    private boolean isTop;

    @ApiModelProperty("是否存草稿")
    private boolean isDraft;

    //字段  字段添加填充内容
    @TableField(fill = FieldFill.INSERT)
    @Alias("createTime")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private ArticleAddVO articleAddVO; // 数据库不存在这个字段

    public Article(ArticleAddVO articleAddVO){
        this.articleAddVO = articleAddVO;
        this.articleId = articleAddVO.getArticleId();
        this.articleTitle = articleAddVO.getArticleTitle();
        this.articleContent = articleAddVO.getArticleContent();
        this.articleCover = articleAddVO.getArticleCover();
        this.categoryId = articleAddVO.getCategoryId();
        this.isTop = articleAddVO.isTop();
        this.isDraft = articleAddVO.isDraft();
    }
}
