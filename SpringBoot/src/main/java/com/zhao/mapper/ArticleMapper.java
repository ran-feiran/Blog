package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.ArticleDTO;
import com.zhao.pojo.Article;
import com.zhao.vo.ArticleAddVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 通过id查询文章
     * @param articleId
     * @return
     */
    ArticleAddVO getArticleById(@Param("articleId") Integer articleId);

    /**
     * 查询文章列表
     * @return
     */
    List<ArticleDTO> getListArticle(@Param("pageNum") Integer pageNum,
                                    @Param("pageSize") Integer pageSize,
                                    @Param("articleTitle") String articleTitle);

    /**
     * 通过id查询标签名字
     * @param id
     * @return
     */
    List<String> getTagNameByArticleId(@Param("id") Integer id);


    /**
     * 查询前台博客
     */
    List<ArticleBlogDTO> listArticles(@Param("current") Integer current);


    /**
     * 通过id查询前台博客
     * @param articleId
     * @return
     */
    ArticleBlogDTO getArticleByIdBlog(@Param("articleId") Integer articleId);
}
