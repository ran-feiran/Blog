package com.zhao.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageBackDTO {

    private Integer id;

    private String pageCover;

    private String pageLabel;

    private String pageName;
}
