package com.zhao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分类数据统计
 *
 * @author ran-feiran
 * @date 2022/10/03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateGoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer categoryId;

    private String categoryName;

    /**
     * 使用该分类的文章数
     */
    private Integer articleCount;
}
