package com.zhao.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    private Integer menuId;

    private String menuName;

    private String menuUrl;

    private String menuComponent;

    private String menuIcon;

    private Integer menuSort;

    private Integer parentId;

    private String description;

    private List<MenuDTO> children = new ArrayList<>();
}
