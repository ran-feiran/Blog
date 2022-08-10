package com.zhao.dto;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Alias("articleId")
    private Integer articleId;

    @Alias("articleTitle")
    private String articleTitle;

    @Alias("createTime")
    private Date createTime;
}
