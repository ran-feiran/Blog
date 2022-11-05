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
public class ArticleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private String articleTitle;

    private String articleCover;

    private String categoryName;

    private List<TagDTO> tagDTOList;

    private Integer viewsCount;

    private Integer likeCount;

    private Integer isTop;

    private Date createTime;

   private Integer type;

   private Integer isDelete;

   private Integer status;
}
