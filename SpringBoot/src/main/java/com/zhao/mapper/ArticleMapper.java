package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.*;
import com.zhao.pojo.Article;
import com.zhao.vo.ArticleAddVO;
import com.zhao.vo.ArticleTopVO;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 通过id获取文章
     * 通过id查询文章
     *
     * @param articleId 文章id
     * @return {@link ArticleBackDTO}
     */
    ArticleBackDTO getArticleById(@Param("articleId") Integer articleId);

    /**
     * 让文章列表
     * 查询文章列表
     *
     * @param conditionVO 条件签证官
     * @return {@link List}<{@link ArticleDTO}>
     */
    List<ArticleDTO> getListArticle(@Param("condition") ConditionVO conditionVO);

    /**
     * 被文章id标记名称
     * 通过id查询标签名字
     *
     * @param id id
     * @return {@link List}<{@link String}>
     */
    List<String> getTagNameByArticleId(@Param("id") Integer id);

    /**
     * 查询前台博客
     */
    List<ArticleBlogDTO> listArticles(@Param("current") Integer current);

    /**
     * 通过id获取文章博客
     * 通过id查询前台博客
     *
     * @param articleId 文章id
     * @return {@link ArticleBlogDTO}
     */
    ArticleBlogDTO getArticleByIdBlog(@Param("articleId") Integer articleId);

    /**
     * 查询上一篇文章
     *
     * @param articleId 文章id
     * @return {@link ArticlePaginationDTO}
     */
    ArticlePaginationDTO selectLastArticle(@Param("articleId") Integer articleId);

    /**
     * 查询下一篇文章
     *
     * @param articleId 文章id
     * @return {@link ArticlePaginationDTO}
     */
    ArticlePaginationDTO selectNextArticle(@Param("articleId") Integer articleId);

    /**
     * 查询最新文章列表
     *
     * @return {@link List}<{@link ArticleRecommendDTO}>
     */
    List<ArticleRecommendDTO> queryNewArticleList();

    /**
     * 查询推荐文章列表
     *
     * @param articleId 文章id
     * @return {@link List}<{@link ArticleRecommendDTO}>
     */
    List<ArticleRecommendDTO> queryRecommendArticleList(@Param("articleId") Integer articleId);

    /**
     * 文章统计
     *
     * @return {@link List<ArticleStatisticsDTO>} 文章统计结果
     */
    List<ArticleStatisticsDTO> listArticleStatistics();

    /**
     * 得到文章
     * 文章列表
     *
     * @param articleId 文章id
     * @return {@link Article}
     */
    Article getArticle(@Param("articleId") Integer articleId);

    /**
     * 数篇文章背
     *
     * @param conditionVO 条件签证官
     * @return {@link Integer}
     */
    Integer countArticleBacks(@Param("condition") ConditionVO conditionVO);

    /**
     * 更新文章删除
     *
     * @param idList   id列表
     * @param isDelete 是删除
     * @return {@link Integer}
     */
    Integer updateArticleDelete(@Param("ids") List<Integer> idList,
                                @Param("isDelete") Integer isDelete);

    /**
     * 通过id删除批处理
     *
     * @param ids id
     * @return {@link Integer}
     */
    Integer deleteBatchById(@Param("ids") List<Integer> ids);

    /**
     * 更新前
     *
     * @param articleTopVO 文章前签证官
     * @return {@link Integer}
     */
    Integer updateTop(@Param("ArticleTop") ArticleTopVO articleTopVO);

    /**
     * 把档案
     *
     * @param current 当前
     * @return {@link List}<{@link ArchivesDTO}>
     */
    List<ArchivesDTO> getArchives(@Param("current") Integer current);
}
