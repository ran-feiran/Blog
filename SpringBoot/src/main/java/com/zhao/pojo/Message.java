package com.zhao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_message")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Message对象", description="留言")
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
    private Date createTime;
}

