package com.zhao.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.*;
import com.zhao.vo.ArticleAddVO;
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
@TableName("tb_new_article")
@ApiModel("文章表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer articleId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("文章内容")
    private String articleContent;

    @ApiModelProperty("文章封面")
    private String articleCover;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("是否置顶")
    private Integer isTop;

    @TableLogic(value = "0" , delval = "1")
    private Integer isDelete;

    @TableField(fill = FieldFill.INSERT)
    @Alias("createTime")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("原文链接")
    private String originalUrl;

}
