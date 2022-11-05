package com.zhao.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ApiModel(value="RoleApi对象", description="")
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_role_resource")
@Accessors(chain = true)
@Builder
public class RoleApi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "//角色id")
    private Integer roleId;

    @ApiModelProperty(value = "//资源id")
    private Integer resourceId;
}
