package com.zhao.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.CategoryService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.CateGoryDTO;
import com.zhao.mapper.CategoryMapper;
import com.zhao.mapper.TagMapper;
import com.zhao.pojo.Category;
import com.zhao.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    TagMapper tagMapper;

    @Override
    public List<CateGoryDTO> listCategories() {
        List<CateGoryDTO> cateGoryDTOS = categoryMapper.listCategories();
        for (CateGoryDTO cateGoryDTO : cateGoryDTOS) {
            Integer articleCount = categoryMapper.queryArticleCountByCategoryId(cateGoryDTO.getCategoryId());
            cateGoryDTO.setArticleCount(articleCount);
        }
        return cateGoryDTOS;
    }

    @Override
    public List<ArticleBlogDTO> listArticles(Integer categoryId, Integer current) {
        List<ArticleBlogDTO> articleBlogDTOS = categoryMapper.listArticles(categoryId, current);
        for (ArticleBlogDTO articleBlogDTO : articleBlogDTOS) {
            List<Tag> tagList = tagMapper.getTagListByArticleId(articleBlogDTO.getArticleId());
            articleBlogDTO.setTagList(tagList);
        }
        return articleBlogDTOS;
    }
}
