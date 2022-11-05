package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.zhao.api.ArticleService;
import com.zhao.api.RedisService;
import com.zhao.api.WebsiteConfigService;
import com.zhao.dto.*;
import com.zhao.mapper.ArticleMapper;
import com.zhao.mapper.ArticleTagMapper;
import com.zhao.mapper.CategoryMapper;
import com.zhao.mapper.TagMapper;
import com.zhao.pojo.*;
import com.zhao.strategy.context.SearchStrategyContext;
import com.zhao.utils.BeanCopyUtil;
import com.zhao.utils.UserUtil;
import com.zhao.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.zhao.constant.CommonConst.FALSE;
import static com.zhao.constant.RedisPrefixConst.*;
import static com.zhao.enums.ArticleStatusEnum.DRAFT;

@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SearchStrategyContext searchStrategyContext;

    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        // 获取网站配置
        WebsiteConfigVO websiteConfig = websiteConfigService.getWebsiteConfig();
        // 保存文章分类
        Category category = saveArticleCategory(articleVO);
        // 保存或修改文章
        Article article = BeanCopyUtil.copyObject(articleVO, Article.class);
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getCategoryId());
        }
        // 设定默认文章封面
        if (StringUtils.isNullOrEmpty(article.getArticleCover())) {
            article.setArticleCover(websiteConfig.getArticleCover());
        }
        article.setUserId(Objects.requireNonNull(UserUtil.getLoginUser()).getUserId());
        // 保存或修改文章
        saveOrUpdate(article);
        // 保存文章标签
        saveArticleTag(articleVO, article.getArticleId());
    }

    /**
     * 保存文章标签
     *
     * @param articleVO 文章信息
     */
    private void saveArticleTag(ArticleVO articleVO, Integer articleId) {
        // 拿到前台的标签列表
        List<String> tagNameList = articleVO.getTagNameList();
        // 判断标签是否存在，不存在则新增
        judgeTagExistAndSet(tagNameList);
        // 保存文章对应的标签列表
        List<Integer> tagList = tagNameList.stream()
                                           .map(item -> tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                                                                            .eq(Tag::getTagName, item))
                                                                            .getTagId())
                                           .collect(Collectors.toList());
        tagList.forEach(item -> {
            ArticleTag articleTag = articleTagMapper.selectOne(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getTagId, item)
                    .eq(ArticleTag::getArticleId, articleId));
            if (Objects.isNull(articleTag)) {
                articleTag = ArticleTag.builder().articleId(articleId).tagId(item).build();
                articleTagMapper.insert(articleTag);
            }
        });
        // 查询数据库中所有的标签与VO中的标签列表比对，有不在VO中的标签则删除一行数据
        List<ArticleTag> articleTags = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, articleId));
        List<Integer> tagIdList = articleTags.stream()
                                             .map(ArticleTag::getTagId)
                                             .collect(Collectors.toList());
        tagIdList.forEach(item -> {
            if (!tagList.contains(item)) {
                articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                        .eq(ArticleTag::getArticleId,articleId)
                        .eq(ArticleTag::getTagId,item));
            }
        });
    }

    /**
     * 判断标签存于否并设置存在
     *
     * @param tagNameList 标记名称列表
     */
    private void judgeTagExistAndSet(List<String> tagNameList) {
        // 判断标签是否存在
        tagNameList.forEach(item -> {
            Tag tag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>().eq(Tag::getTagName, item));
            if (Objects.isNull(tag)) {
                tag = Tag.builder().tagName(item).build();
                tagMapper.insert(tag);
            }
        });
    }

    /**
     * 保存文章分类
     *
     * @param articleVO 文章信息
     * @return {@link Category} 文章分类
     */
    private Category saveArticleCategory(ArticleVO articleVO) {
        // 判断分类是否存在
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().eq(Category::getCategoryName, articleVO.getCategoryName()));
        if (Objects.isNull(category) && !articleVO.getStatus().equals(DRAFT.getStatus())) {
            category = Category.builder().categoryName(articleVO.getCategoryName()).build();
            categoryMapper.insert(category);
        }
        return category;
    }

    @Override
    public ArticleBackDTO getArticleById(Integer articleId) {
        return articleMapper.getArticleById(articleId);
    }

    @Override
    public PageDTO<ArticleDTO> getListArticle(ConditionVO conditionVO) {
        // 查询文章总量
        Integer count = articleMapper.countArticleBacks(conditionVO);
        if (count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        // 查询后台文章
        conditionVO.setCurrent( (conditionVO.getCurrent() - 1) * conditionVO.getSize());
        List<ArticleDTO> articleDTOList = articleMapper.getListArticle(conditionVO);
        // 查询文章点赞量和浏览量
        Map<Object, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT);
        Map<String, Object> likeCountMap = redisService.hGetAll(ARTICLE_LIKE_COUNT);
        // 封装点赞量和浏览量
        articleDTOList.forEach(item -> {
                    item.setLikeCount((Integer) likeCountMap.get(item.getArticleId().toString()));
                    Double viewsCount = viewsCountMap.get(item.getArticleId().toString());
                    if (Objects.nonNull(viewsCount)) {
                        item.setViewsCount(viewsCount.intValue());
                    }
                });
        return new PageDTO<>(articleDTOList, count);
    }

    @Override
    public List<ArticleBlogDTO> listArticles(Integer current) {
        return articleMapper.listArticles(current);
    }

    @Override
    public PageDTO<ArchivesDTO> getArchives(Integer current) {
        // 查询未删除且公开的文章数量
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getStatus, 1));
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>(),0);
        }
        // 查询归档文章数据
        return new PageDTO<>(articleMapper.getArchives(current),count);
    }

    @Override
    public ArticleBlogDTO getArticleByIdBlog(Integer articleId) throws Exception {
        // 查询推荐文章
        // ============================
        CompletableFuture<List<ArticleRecommendDTO>> recommendArticleList = CompletableFuture
                .supplyAsync(() -> articleMapper.queryRecommendArticleList(articleId));
        // 查询最新文章
        // ============================
        CompletableFuture<List<ArticleRecommendDTO>> newArticleList = CompletableFuture
                .supplyAsync(() -> articleMapper.queryNewArticleList());
        // 查询文章信息
        ArticleBlogDTO articleByIdBlog = articleMapper.getArticleByIdBlog(articleId);
        // 查询文章对应的标签
        // 查询上一篇下一篇文章
        ArticlePaginationDTO lastArticle = articleMapper.selectLastArticle(articleId);
        ArticlePaginationDTO nextArticle = articleMapper.selectNextArticle(articleId);
        // 封装上一篇和下一篇文章
        if (Objects.isNull(lastArticle)) {
            articleByIdBlog.setLastArticle(new ArticlePaginationDTO());
        } else {
            articleByIdBlog.setLastArticle(lastArticle);
        }
        if (Objects.isNull(nextArticle)) {
            articleByIdBlog.setNextArticle(new ArticlePaginationDTO());
        } else {
            articleByIdBlog.setNextArticle(nextArticle);
        }
        // 每次刷新都增加一次访问量  这里选用redis的 zset 数据类型
        redisService.zIncr(ARTICLE_VIEWS_COUNT,articleId.toString(),1D);
        // 拿到文章的访问量
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId.toString());
        // 拿到文章的点赞量
        Integer likeCount = (Integer) redisService.hGet(ARTICLE_LIKE_COUNT, articleId.toString());
        // 封装的点赞量和访问量
        if (Objects.nonNull(score)) {
            articleByIdBlog.setViewsCount(score.intValue());
        } else {
            articleByIdBlog.setViewsCount(FALSE);
        }
        articleByIdBlog.setLikeCount(likeCount);
        // 封装推荐文章和最新文章列表
        try {
            articleByIdBlog.setNewestArticleList(newArticleList.get());
            articleByIdBlog.setRecommendArticleList(recommendArticleList.get());
        } catch (InterruptedException e) {
            log.error("堆栈信息：{}",e.getMessage());
        }
        return articleByIdBlog;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveLikeArticle(Integer articleId) {
        User loginUser = UserUtil.getLoginUser();
        assert loginUser != null;
        String articleLikeKey = ARTICLE_USER_LIKE + loginUser.getUserId();
        if (redisService.sIsMember(articleLikeKey,articleId)) {
            // 点过赞则删除评论id
            redisService.sRemove(articleLikeKey, articleId);
            // 评论点赞量-1
            redisService.hDecr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisService.sAdd(articleLikeKey, articleId);
            // 评论点赞量+1
            redisService.hIncr(ARTICLE_LIKE_COUNT, articleId.toString(), 1L);
        }
    }

    @Override
    public List<ArticleSearchDTO> getArticlesBySearch(ConditionVO conditionVO) throws IOException {
        return searchStrategyContext.executeSearchStrategy(conditionVO.getKeywords());
    }

    @Override
    public List<ArticleStatisticsDTO> listArticleStatistics() {
        return articleMapper.listArticleStatistics();
    }

    @Override
    public Article getArticle(Integer articleId) {
        return articleMapper.getArticle(articleId);
    }

    @Override
    public void updateArticleDelete(DeleteVO deleteVO) {
        articleMapper.updateArticleDelete(deleteVO.getIdList(), deleteVO.getIsDelete());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatchById(List<Integer> ids) {
        articleMapper.deleteBatchById(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTop(ArticleTopVO articleTopVO) {
        articleMapper.updateTop(articleTopVO);
    }


}
