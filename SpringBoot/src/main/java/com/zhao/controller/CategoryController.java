package com.zhao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.jdbc.StringUtils;
import com.zhao.api.ArticleService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.CateGoryDTO;
import com.zhao.pojo.Tag;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.CategoryService;
import com.zhao.exception.div.ServiceException;
import com.zhao.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 前台查询分类
     * @return
     */
    @GetMapping("/listCategories")
    public Result listCategories() {
        List<CateGoryDTO> cateGoryDTOS = categoryService.listCategories();
        long count = categoryService.count();
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList", cateGoryDTOS);
        map.put("count",count);
        return Result.success(map,"");
    }

    /**
     * 查询分类列表
     * @return
     */
    @GetMapping("/getCategoryList")
    public Result getTagList() {
        List<Category> categoryList = categoryService.list();
        if (categoryList != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("categoryList",categoryList);
            return Result.success(map,"获取分类成功");
        }
        throw new ServiceException(ResultInfo.CODE_600,"获取分类失败");
    }

    /**
     * 删除分类通过id
     * @param categoryId
     * @return
     */
    @DeleteMapping("/deleteCategory")
    public Result deleteCategory(@RequestParam("categoryId") Integer categoryId) {
        try {
            categoryService.removeById(categoryId);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"删除分类失败");
        }
        return Result.success();
    }

    /**
     * 批量删除分类
     * @param ids
     * @return
     */
    @PostMapping("/del/batch")
    public Result delBatch(@RequestBody List<Integer> ids) {
        if (ids.size() <= 0) {
            throw new ServiceException(ResultInfo.CODE_600,"批量删除分类失败");
        }
        try {
            categoryService.removeBatchByIds(ids);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"批量删除分类失败");
        }
        return Result.success();
    }

    /**
     * 增加或更新分类
     * @param category
     * @return
     */
    @PostMapping("/addOrEditCategory")
    public Result addOrEditCategory(@RequestBody Category category) {
        try {
            categoryService.saveOrUpdate(category);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"操作分类失败");
        }
        return Result.success();
    }


    /**
     * 分页查询分类列表
     * @param pageNum
     * @param pageSize
     * @param categoryName
     * @return
     */
    @GetMapping("/listCategory")
    public Result listCategory(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "categoryName",defaultValue = "") String categoryName) {
        Page<Category> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Category> wrapper = null;
        if (!StringUtils.isNullOrEmpty(categoryName)) {
            wrapper = new QueryWrapper<>();
            wrapper.like("category_name",categoryName);
        }
        Page<Category> categoryList = null;
        try {
            categoryList = categoryService.page(page, wrapper);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"获取分类失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("categoryList",categoryList);
        return Result.success(map,"success");
    }

    @GetMapping("/{categoryId}")
    public Result getCategoryById(@PathVariable("categoryId") Integer categoryId,
                                  @RequestParam("current") Integer current) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.select("category_name").eq("category_id",categoryId);
        Category one = categoryService.getOne(wrapper);
        current = current - 1;
        List<ArticleBlogDTO> articleBlogDTOS = categoryService.listArticles(categoryId,current);
        Map<String, Object> map = new HashMap<>();
        map.put("articleList", articleBlogDTOS);
        map.put("name", one.getCategoryName());
        return Result.success(map,"");
    }
}
