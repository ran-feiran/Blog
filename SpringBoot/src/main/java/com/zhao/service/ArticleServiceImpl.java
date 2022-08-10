package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.RedisService;
import com.zhao.constant.RedisPrefixConst;
import com.zhao.dto.ArchivesDTO;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.mapper.TagMapper;
import com.zhao.pojo.Tag;
import com.zhao.pojo.User;
import com.zhao.result.ResultInfo;
import com.zhao.api.ArticleService;
import com.zhao.dto.ArticleDTO;
import com.zhao.exception.div.ServiceException;
import com.zhao.mapper.ArticleMapper;
import com.zhao.mapper.ArticleTagMapper;
import com.zhao.pojo.Article;
import com.zhao.pojo.ArticleTag;
import com.zhao.utils.UserUtil;
import com.zhao.vo.ArticleAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleTagMapper articleTagMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    RedisService redisService;

    @Override
    public int saveOrUpdateAndSetTagIdList(ArticleAddVO articleAddVO) {
        Article article = new Article(articleAddVO);
        if (article.getCategoryId() == null) {
            article.setCategoryId(1);
        }

        List<Integer> tagIdList = articleAddVO.getTagIdList(); // 获得前端所有标签列表

        boolean flag = saveOrUpdate(article);
        if (!flag) {
            throw new ServiceException(ResultInfo.CODE_500,"系统错误");
        }
        Integer articleId = article.getArticleId();
        System.out.println("文章id"+articleId);
        System.out.println(tagIdList);

        if (tagIdList.size() > 0){
            for (Integer tagId : tagIdList) {
                QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
                wrapper.eq("article_id",articleId);
                wrapper.eq("tag_id",tagId);
                if (articleTagMapper.selectOne(wrapper) == null) {
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticleId(articleId);
                    articleTag.setTagId(tagId);
                    int insert = articleTagMapper.insert(articleTag);
                    if (insert <= 0) {
                        throw new ServiceException(ResultInfo.CODE_500,"系统错误");
                    }
                }
            }
            QueryWrapper<ArticleTag> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("article_id",articleId);
            List<ArticleTag> articleTags = articleTagMapper.selectList(wrapper1);
            List<Integer> collect = articleTags.stream().
                    map(res -> res.getTagId()).
                    collect(Collectors.toList());
            for (Integer integer : collect) {
                if (!tagIdList.contains(integer)) {
                    QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
                    wrapper.eq("article_id",articleId);
                    wrapper.eq("tag_id",integer);
                    int i = articleTagMapper.delete(wrapper);
                    if (i <= 0) {
                        throw new ServiceException(ResultInfo.CODE_500,"系统错误");
                    }
                }
            }
            return articleId;
        }
        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleId(articleId);
        articleTag.setTagId(5);
        int insert = articleTagMapper.insert(articleTag);
        if (insert <= 0) {
            throw new ServiceException(ResultInfo.CODE_500,"系统错误");
        }
        return articleId;
    }

    @Override
    public ArticleAddVO getArticleById(Integer articleId) {
        return articleMapper.getArticleById(articleId);
    }

    @Override
    public List<ArticleDTO> getListArticle(Integer pageNum, Integer pageSize, String articleTitle) {
        List<ArticleDTO> article = null;
        try {
            article = articleMapper.getListArticle(pageNum, pageSize, articleTitle);
            for (ArticleDTO articleDTO : article) {
                List<String> tagName = articleMapper.
                        getTagNameByArticleId(articleDTO.getArticleId());
                articleDTO.setTagName(tagName);
            }
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_500,"系统错误");
        }
        return article;
    }

    @Override
    public List<ArticleBlogDTO> listArticles(Integer current) {
        List<ArticleBlogDTO> articleBlogDTOS = articleMapper.listArticles(current);
        for (ArticleBlogDTO articleBlogDTO : articleBlogDTOS) {
            List<Tag> tagList = tagMapper.getTagListByArticleId(articleBlogDTO.getArticleId());
            articleBlogDTO.setTagList(tagList);
        }
        return articleBlogDTOS;
    }

    @Override
    public Page<Article> getArchives(Integer current) {
        return articleMapper.selectPage(new Page<>(current, 10), null);
    }

    @Override
    public ArticleBlogDTO getArticleByIdBlog(Integer articleId) {
        ArticleBlogDTO articleByIdBlog = articleMapper.getArticleByIdBlog(articleId);
        List<Tag> tagList = tagMapper.getTagListByArticleId(articleByIdBlog.getArticleId());
        articleByIdBlog.setTagList(tagList);
        // 每次刷新都增加一次访问量
        redisService.hIncr(RedisPrefixConst.ARTICLE_VIEWS_COUNT, articleId.toString(),1L);
        // 拿到所有文章的访问量
        Map<String, Object> viewsMap = redisService.hGetAll(RedisPrefixConst.ARTICLE_VIEWS_COUNT);
        // 拿到所有文章的点赞量
        Map<String, Object> likeMap = redisService.hGetAll(RedisPrefixConst.ARTICLE_LIKE_COUNT);
        articleByIdBlog.setViewsCount((Integer) viewsMap.get(articleId.toString()));
        articleByIdBlog.setLikeCount((Integer) likeMap.get(articleId.toString()));
        return articleByIdBlog;
    }

    @Override
    public void saveLikeArticle(Integer articleId) {
        User loginUser = UserUtil.getLoginUser();
        assert loginUser != null;
        String articleLikeKey = RedisPrefixConst.ARTICLE_USER_LIKE + loginUser.getUserId();
        if (redisService.sIsMember(articleLikeKey,articleId)) {
            // 点过赞则删除评论id
            redisService.sRemove(articleLikeKey, articleId);
            // 评论点赞量-1
            redisService.hDecr(RedisPrefixConst.ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisService.sAdd(articleLikeKey, articleId);
            // 评论点赞量+1
            redisService.hIncr(RedisPrefixConst.ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }
}
