package com.zhao.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhao.annotations.OptLog;
import com.zhao.api.CategoryService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.CateGoryDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.PageListDTO;
import com.zhao.pojo.Category;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zhao.constant.OptTypeConst.REMOVE;
import static com.zhao.constant.OptTypeConst.SAVE_OR_UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;


/**
 * 分类控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@RequestMapping("/category")
@Api(tags = "分类模块")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "分类搜索")
    @GetMapping("/search")
    public ResultStandby<List<Category>> searchCategories(@RequestParam("keywords") String keywords) {
        return success(categoryService.searchCategories(keywords), SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取分类列表")
    @GetMapping("/listCategories")
    public ResultStandby<PageDTO<CateGoryDTO>> listCategories() {
        return success(new PageDTO<>(categoryService.listCategories(),categoryService.count()),SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取分类数据(后台)")
    @GetMapping("/getCategoryList")
    public ResultStandby<List<Category>> getCategoryList() {
        return success(categoryService.list(),SUCCESS.getDesc());
    }

    @ApiOperation(value = "物理删除分类")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/del/batch")
    public ResultStandby<?> delBatch(@RequestBody List<Integer> ids) {
        categoryService.removeBatchByIds(ids);
        return success(null,SUCCESS.getDesc());
    }

    @ApiOperation(value = "新增或更新分类")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/addOrEditCategory")
    public ResultStandby<?> addOrEditCategory(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取分类列表(后台)")
    @GetMapping("/listCategory")
    public ResultStandby<PageDTO<CateGoryDTO>> listCategory(ConditionVO conditionVO) {
        return success(categoryService.queryPageCategories(conditionVO),SUCCESS.getDesc());
    }

    @ApiOperation(value = "通过分类id获取对应文章")
    @GetMapping("/query/{categoryId}")
    public ResultStandby<PageListDTO<ArticleBlogDTO>> getCategoryById(@PathVariable("categoryId") Integer categoryId, @RequestParam("current") Integer current) {
        return success(new PageListDTO<>(categoryService.listArticles(categoryId, current - 1), categoryService
                .getOne(new LambdaQueryWrapper<Category>().eq(Category::getCategoryId, categoryId)).getCategoryName()), SUCCESS.getDesc());
    }
}
