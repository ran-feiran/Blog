package com.zhao.dto;

import com.zhao.vo.PageVO;
import com.zhao.vo.WebsiteConfigVO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BlogHomeInfo对象",description = "博客信息")
public class BlogHomeInfoDTO {

    /**
     * 文章数量
     */
    private Long articleCount;

    /**
     * 分类数量
     */
    private Long categoryCount;

    /**
     * 标签数量
     */
    private Long tagCount;

    /**
     * 独特访客
     */
    private Long uniqueVisitor;

    /**
     * 访问量
     */
    private Integer viewsCount;

    /**
     * 网站配置签证官
     */
    private WebsiteConfigVO websiteConfig;

    /**
     * 页面列表
     */
    private List<PageVO> pageList;


}