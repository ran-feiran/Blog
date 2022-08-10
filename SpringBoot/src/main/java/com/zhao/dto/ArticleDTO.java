package com.zhao.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private String articleTitle;

    private String categoryName;

    private List<String> tagName;

    private boolean isTop;

    private Date createTime;

    private Date updateTime;
}
