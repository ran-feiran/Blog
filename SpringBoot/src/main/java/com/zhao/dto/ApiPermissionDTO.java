package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiPermissionDTO {

    private Integer id;

    private String url;

    private String requestMethod;

    private List<String> roleList;
}
