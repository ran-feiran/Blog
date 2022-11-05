package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleBackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private String roleName;

    private String roleLabel;

    private Integer isDisable;

    private Date createTime;

    private List<Integer> menuIdList;

    private List<Integer> resourceIdList;

}
