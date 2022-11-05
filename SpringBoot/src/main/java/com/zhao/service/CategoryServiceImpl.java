package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.CategoryService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.CateGoryDTO;
import com.zhao.dto.PageDTO;
import com.zhao.mapper.CategoryMapper;
import com.zhao.mapper.TagMapper;
import com.zhao.pojo.Category;
import com.zhao.pojo.Tag;
import com.zhao.vo.ConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

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
        return categoryMapper.listArticles(categoryId, current);
    }

    @Override
    public PageDTO<CateGoryDTO> queryPageCategories(ConditionVO conditionVO) {
        Long count = categoryMapper.selectCount(null);
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        List<CateGoryDTO> cateGoryDTOS = categoryMapper.queryPageCategories(conditionVO);
        cateGoryDTOS.forEach(item -> {
            item.setArticleCount(categoryMapper.queryArticleCountByCategoryId(item.getCategoryId()));
        });
        return new PageDTO<>(cateGoryDTOS, count);
    }

    @Override
    public List<Category> searchCategories(String keywords) {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>().like(Category::getCategoryName,keywords));
    }
}
