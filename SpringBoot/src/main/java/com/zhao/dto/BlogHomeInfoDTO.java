package com.zhao.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BlogHomeInfo对象",description = "博客信息")
public class BlogHomeInfoDTO {
    /**
     * 博主昵称
     */
    private String nickname;

    /**
     * 博主头像
     */
    private String avatar;

    /**
     * 博主简介
     */
    private String intro;

    /**
     * 文章数量
     */
    private long articleCount;

    /**
     * 分类数量
     */
    private long categoryCount;

    /**
     * 标签数量
     */
    private long tagCount;

    /**
     * 公告
     */
    private String notice;

    /**
     * 访问量
     */
    private Integer viewsCount;


}