package com.zhao.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_comment")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Comment对象", description="")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "//评论主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "//用户id")
    private Integer userId;

    @ApiModelProperty(value = "//文章id")
    private Integer articleId;

    @ApiModelProperty(value = "//评论内容")
    private String commentContent;

    @ApiModelProperty(value = "//评论时间")
    private Date createTime;

    @ApiModelProperty(value = "//回复用户id")
    private Integer replyId;

    @ApiModelProperty(value = "//父评论id")
    private Integer parentId;

    @ApiModelProperty(value = "//是否删除")
    @TableLogic(value = "0", delval = "1")
    private Boolean isDelete;

    @ApiModelProperty(value = "//类型")
    private Integer type;

    @ApiModelProperty(value = "//审核")
    private Integer isReview;


}

