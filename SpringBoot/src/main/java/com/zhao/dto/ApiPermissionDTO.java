package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiPermissionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String url;

    private String requestMethod;

    private Integer isAnonymous;

    private List<String> roleList;
}
