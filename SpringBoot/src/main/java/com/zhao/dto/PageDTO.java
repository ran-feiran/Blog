package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {

    /**
     * 分页列表
     */
    private List<T> recordList;

    /**
     * 总数
     */
    private long count;


    public PageDTO(List<T> recordList) {
        this.recordList = recordList;
    }


}