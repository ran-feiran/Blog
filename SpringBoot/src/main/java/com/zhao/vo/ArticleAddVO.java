package com.zhao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAddVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private String articleTitle;

    private String articleContent; // 对应mysql中的longtext

    private String articleCover;

    private Integer categoryId;

    private boolean isTop;

    private boolean isDraft;

    private List<Integer> tagIdList;
}
