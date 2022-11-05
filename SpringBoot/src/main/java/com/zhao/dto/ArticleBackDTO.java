package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private String articleTitle;

    private String articleContent;

    private String articleCover;

    private String categoryName;

    private List<String> tagNameList;

    private String originalUrl;

    private Integer isTop;

    private Integer type;

    private Integer status;
}
