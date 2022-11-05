package com.zhao.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhao.api.BlogInfoService;
import com.zhao.api.PageService;
import com.zhao.api.RedisService;
import com.zhao.api.WebsiteConfigService;
import com.zhao.dto.ArticleRecommendDTO;
import com.zhao.dto.BlogHomeInfoDTO;
import com.zhao.mapper.ArticleMapper;
import com.zhao.mapper.CategoryMapper;
import com.zhao.mapper.TagMapper;
import com.zhao.pojo.Article;
import com.zhao.vo.PageVO;
import com.zhao.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.zhao.constant.RedisPrefixConst.BLOG_VIEWS_COUNT;
import static com.zhao.constant.RedisPrefixConst.UNIQUE_VISITOR;

@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PageService pageService;
    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Override
    public BlogHomeInfoDTO getBlogInfo() throws ExecutionException, InterruptedException {
        // 获取网站背景信息
        List<PageVO> pageList = pageService.getPageList();
        // 获取网站配置信息
        CompletableFuture<WebsiteConfigVO> async = CompletableFuture.supplyAsync(() -> websiteConfigService.getWebsiteConfig());
        //获取文章数量
        Long articleCount= articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getStatus, 1));
        //分类数量
        Long categoryCount = categoryMapper.selectCount(null);
        //标签数量
        Long tagCount = tagMapper.selectCount(null);
        // 访问量
        Integer viewsCount =(Integer) redisService.get(BLOG_VIEWS_COUNT);
        // 获取今日访客数
        Long uniqueVisitor = redisService.sSize(UNIQUE_VISITOR);
        return new BlogHomeInfoDTO(
                articleCount,
                categoryCount,
                tagCount,
                uniqueVisitor,
                viewsCount,
                async.get(),
                pageList);
    }

    @Override
    public List<ArticleRecommendDTO> getNewArticleList() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> articleMapper.queryNewArticleList()).get();
    }
}
