package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.*;
import com.zhao.pojo.Article;
import com.zhao.vo.ArticleTopVO;
import com.zhao.vo.ArticleVO;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.DeleteVO;

import java.io.IOException;
import java.util.List;

/**
 * 文章服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface ArticleService extends IService<Article> {

    /**
     * 保存或更新文章
     * 增加或更新博客并且设置文章对应的标签
     *
     * @param articleVO 文章签证官
     */
    void saveOrUpdateArticle(ArticleVO articleVO);

    /**
     * 通过id查询文章
     *
     * @param articleId 文章id
     * @return {@link ArticleBackDTO}
     */
    ArticleBackDTO getArticleById(Integer articleId);


    /**
     * 让文章列表
     * 查询文章列表
     *
     * @param conditionVO 条件签证官
     * @return {@link PageDTO}<{@link ArticleDTO}>
     */
    PageDTO<ArticleDTO> getListArticle(ConditionVO conditionVO);

    /**
     * 文章列表
     * 查询前台博客
     *
     * @param current 当前
     * @return {@link List}<{@link ArticleBlogDTO}>
     */
    List<ArticleBlogDTO> listArticles(Integer current);


    /**
     * 把档案
     * 归档
     *
     * @param current 当前
     * @return {@link PageDTO}<{@link ArchivesDTO}>
     */
    PageDTO<ArchivesDTO> getArchives(Integer current);


    /**
     * 通过id获取文章博客
     * 通过id查询前台博客
     *
     * @param articleId 文章id
     * @return ArticleBlogDTO
     * @throws Exception 异常
     */
    ArticleBlogDTO getArticleByIdBlog(Integer articleId) throws Exception;

    /**
     * 拯救像文章
     * 保存用户点赞的文章
     *
     * @param articleId 文章id
     */
    void saveLikeArticle(Integer articleId);

    /**
     * 搜索文章
     *
     * @param conditionVO 查询条件
     */
    List<ArticleSearchDTO> getArticlesBySearch(ConditionVO conditionVO) throws IOException;

    /**
     * 文章贡献统计
     *
     * @return {@link List}<{@link ArticleStatisticsDTO}>
     */
    List<ArticleStatisticsDTO> listArticleStatistics();

    /**
     * 文章列表
     *
     * @return {@link Article}
     */
    Article getArticle(Integer articleId);

    /**
     * 更新文章删除
     *
     * @param deleteVO 删除签证官
     */
    void updateArticleDelete(DeleteVO deleteVO);

    /**
     * 彻底删除
     *
     * @param ids id
     */
    void deleteBatchById(List<Integer> ids);

    /**
     * 更新置顶
     *
     * @param articleTopVO 文章前签证官
     */
    void updateTop(ArticleTopVO articleTopVO);
}
