package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.CateGoryDTO;
import com.zhao.dto.PageDTO;
import com.zhao.pojo.Category;
import com.zhao.vo.ConditionVO;

import java.util.List;

/**
 * 分类服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取分类列表
     *
     * @return {@link List}<{@link CateGoryDTO}>
     */
    List<CateGoryDTO> listCategories();

    /**
     * 获得文章通过指定分类id
     *
     * @param categoryId 类别id
     * @param current    当前
     * @return {@link List}<{@link ArticleBlogDTO}>
     */
    List<ArticleBlogDTO> listArticles(Integer categoryId, Integer current);

    /**
     * 查询页面类别
     * 分页查询分类数据
     *
     * @param conditionVO 条件签证官
     * @return {@link PageDTO}<{@link CateGoryDTO}>
     */
    PageDTO<CateGoryDTO> queryPageCategories(ConditionVO conditionVO);

    /**
     * 搜索分类
     *
     * @param keywords 关键字
     * @return {@link List}<{@link Category}>
     */
    List<Category> searchCategories(String keywords);
}

