package com.zhao.dto;


import com.zhao.pojo.Tag;
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

    private List<Tag> tagList;

    private boolean isTop;

    private Date createTime;

    private Date updateTime;

    // 访问量
    private Integer viewsCount;

    // 点赞数
    private Integer likeCount;

}
