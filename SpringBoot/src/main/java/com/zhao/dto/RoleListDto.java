package com.zhao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String roleName;
    private Integer size;
}
