package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.CateGoryDTO;
import com.zhao.pojo.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CateGoryDTO> listCategories();

    List<ArticleBlogDTO> listArticles(Integer categoryId, Integer current);
}

