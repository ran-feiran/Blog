package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.CateGoryDTO;
import com.zhao.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    List<CateGoryDTO> listCategories();

    Integer queryArticleCountByCategoryId(@Param("id") Integer id);

    /**
     * 查询前台分类对应的博客
     */
    List<ArticleBlogDTO> listArticles(@Param("categoryId") Integer categoryId,
                                      @Param("current") Integer current);
}
