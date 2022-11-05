package com.zhao.dto;

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

    private Integer articleId;

    private String articleTitle;

    private Date createTime;
}
