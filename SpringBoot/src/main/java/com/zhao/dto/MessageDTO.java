package com.zhao.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MessageDTO对象", description="留言")
public class MessageDTO implements Serializable {

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

    @ApiModelProperty(value = "//发布时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("//是否审核")
    private Integer isReview;

    @ApiModelProperty("//倍速")
    private Integer time;
}
