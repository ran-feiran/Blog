package com.zhao.pojo;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
@ApiModel("用户表")
@Accessors(chain = true)
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer userId;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String intro;

    private String description;

    private String webSite;

    private String email;

    private String ipAddress;

    private String ipSource;

    private Date lastLoginTime;

    /**
     * 点赞文章集合
     */
    @TableField(exist = false)
    private Set<Object> articleLikeSet;

    /**
     * 点赞评论集合
     */
    @TableField(exist = false)
    private Set<Object> commentLikeSet;

    //字段  字段添加填充内容
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic(value = "0", delval = "1")
    private Integer isDelete;

    @ApiModelProperty("禁言")
    private Integer isSilence;

    @ApiModelProperty("登录类型")
    private Integer loginType;
}
