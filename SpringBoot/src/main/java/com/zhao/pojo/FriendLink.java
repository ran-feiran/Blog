package com.zhao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_friend_link")
@ApiModel(value="FriendLink对象", description="友链")
@AllArgsConstructor
@NoArgsConstructor
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "友链主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "链接名")
    private String linkName;

    @ApiModelProperty(value = "链接头像")
    private String linkAvatar;

    @ApiModelProperty(value = "链接地址")
    private String linkAddress;

    @ApiModelProperty(value = "链接简介")
    private String linkIntro;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}