package com.zhao.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_message")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Message对象", description="留言")
@Builder
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "//ip")
    private String ipAddress;

    @ApiModelProperty(value = "//ip地理位置")
    private String ipSource;

    @ApiModelProperty(value = "//昵称")
    private String nickname;

    @ApiModelProperty(value = "//头像")
    private String avatar;

    @ApiModelProperty(value = "//留言内容")
    private String messageContent;

    @ApiModelProperty(value = "//弹幕速度")
    private Integer time;

    @ApiModelProperty(value = "//发布时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "//更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("//是否审核")
    private Integer isReview;
}

