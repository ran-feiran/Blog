package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.TagBackDTO;
import com.zhao.pojo.Tag;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标签服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface TagService extends IService<Tag> {

    /**
     * 文章列表
     * <p>
     * 通过标签id获取对应的文章
     *
     * @param tagId   标签id
     * @param current 当前
     * @return {@link List}<{@link ArticleBlogDTO}>
     */
    List<ArticleBlogDTO> listArticles(Integer tagId, Integer current);

    /**
     * 查询页面标签
     * 查询页面类别
     *
     * @param conditionVO 条件签证官
     * @return {@link PageDTO}<{@link TagBackDTO}>
     */
    PageDTO<TagBackDTO> queryPageTags(ConditionVO conditionVO);


    /**
     * 搜索标签
     *
     * @param keywords 关键字
     * @return {@link List}<{@link Tag}>
     */
    List<Tag> searchTags(String keywords);
}
