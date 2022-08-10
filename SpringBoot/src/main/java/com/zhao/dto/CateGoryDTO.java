package com.zhao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateGoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer categoryId;

    private String categoryName;

    /**
     * 使用该标签的文章数
     */
    private Integer articleCount;
}
