package com.zhao.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 留言
 */
@Data
@ApiModel(description = "留言")
@AllArgsConstructor
@NoArgsConstructor
public class MessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    @ApiModelProperty(name = "nickname", value = "昵称", required = true, dataType = "String")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(name = "avatar", value = "头像", required = true, dataType = "String")
    private String avatar;

    /**
     * 留言内容
     */
    @ApiModelProperty(name = "messageContent", value = "留言内容", required = true, dataType = "String")
    private String messageContent;

    /**
     * 弹幕速度
     */
    @ApiModelProperty(name = "time", value = "弹幕速度", required = true, dataType = "Integer")
    private Integer time;

}
