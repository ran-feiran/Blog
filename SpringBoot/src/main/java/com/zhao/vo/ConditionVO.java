package com.zhao.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询条件
 *
 * @author ran-feiran
 * @date 2022/09/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询条件")
public class ConditionVO {

    /**
     * 页码
     */
    @ApiModelProperty(name = "current", value = "页码", dataType = "Long")
    private Long current;

    /**
     * 条数
     */
    @ApiModelProperty(name = "size", value = "条数", dataType = "Long")
    private Long size;

    /**
     * 搜索内容
     */
    @ApiModelProperty(name = "keywords", value = "搜索内容", dataType = "String")
    private String keywords;

    /**
     * 类型
     */
    @ApiModelProperty(name = "type", value = "类型", dataType = "Integer")
    private Integer type;

    /**
     * 类型
     */
    @ApiModelProperty(name = "loginType", value = "登录类型", dataType = "Integer")
    private Integer loginType;

    /**
     * 分类id
     */
    @ApiModelProperty(name = "categoryId", value = "分类id", dataType = "Integer")
    private Integer categoryId;

    /**
     * 标签id
     */
    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Integer")
    private Integer tagId;


    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "状态", dataType = "Integer")
    private Integer status;


    /**
     * 是否删除
     */
    @ApiModelProperty(name = "isDelete", value = "是否删除", dataType = "Integer")
    private Integer isDelete;

    /**
     * 是否审核
     */
    @ApiModelProperty(name = "isReview", value = "是否审核", dataType = "Integer")
    private Integer isReview;
}
