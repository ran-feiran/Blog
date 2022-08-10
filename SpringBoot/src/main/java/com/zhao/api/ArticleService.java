package com.zhao.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.ArchivesDTO;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.ArticleDTO;
import com.zhao.pojo.Article;
import com.zhao.vo.ArticleAddVO;

import java.util.List;

public interface ArticleService extends IService<Article> {
    /**
     *增加或更新博客并且设置文章对应的标签
     */
    int saveOrUpdateAndSetTagIdList(ArticleAddVO articleAddVO);

    /**
     * 通过id查询文章
     * @param articleId
     * @return
     */
    ArticleAddVO getArticleById(Integer articleId);


    /**
     * 查询文章列表
     * @return
     */
    List<ArticleDTO> getListArticle(Integer pageNum,
                                    Integer pageSize,
                                    String articleTitle);

    /**
     * 查询前台博客
     */
    List<ArticleBlogDTO> listArticles(Integer current);


    /**
     * 归档
     * @param current
     * @return
     */
    Page<Article> getArchives(Integer current);


    /**
     * 通过id查询前台博客
     * @param articleId
     * @return ArticleBlogDTO
     */
    ArticleBlogDTO getArticleByIdBlog(Integer articleId);

    /**
     * 保存用户点赞的文章
     * @param articleId
     */
    void saveLikeArticle(Integer articleId);
}
