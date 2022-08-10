package com.zhao.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.dto.ArchivesDTO;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.pojo.Article;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.ArticleService;
import com.zhao.dto.ArticleDTO;
import com.zhao.exception.div.ServiceException;
import com.zhao.mapper.ArticleTagMapper;
import com.zhao.pojo.ArticleTag;
import com.zhao.vo.ArticleAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleService articleService;

    @PostMapping("/like/{articleId}")
    public Result saveLikeArticle(@PathVariable("articleId") Integer articleId) {
        articleService.saveLikeArticle(articleId);
        return Result.success();
    }

    @GetMapping("/blog/{articleId}")
    public Result getArticleByIdBlog(@PathVariable("articleId") Integer articleId) {
        ArticleBlogDTO articleByIdBlog = articleService.getArticleByIdBlog(articleId);
        Map<String, Object> map = new HashMap<>();
        map.put("data",articleByIdBlog);
        return Result.success(map,"");
    }

    @GetMapping("/archives")
    public Result getArchives(@RequestParam("current") Integer current) {
        Page<Article> archives = articleService.getArchives(current);
        List<Article> records = archives.getRecords();
        List<ArchivesDTO> archivesDTOS = new ArrayList<>();
        for (Article record : records) {
            ArchivesDTO archivesDTO = new ArchivesDTO();
            BeanUtil.copyProperties(record, archivesDTO, false);
            archivesDTOS.add(archivesDTO);
        }
        System.out.println(Arrays.toString(archivesDTOS.toArray()));
        long total = archives.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("archiveList", archivesDTOS);
        map.put("count", total);
        return Result.success(map,"");
    }

    @GetMapping("/articles")
    public Result listArticles(@RequestParam("current") Integer current) {
        current = current - 1;
        List<ArticleBlogDTO> articleBlogDTOS = articleService.listArticles(current);
        Map<String, Object> map = new HashMap<>();
        map.put("articleList",articleBlogDTOS);
        return Result.success(map,"");
    }

    @PostMapping("/saveOrUpdateArticle")
    public Result saveOrUpdateArticle(@RequestBody ArticleAddVO articleAddVO) {
        //articleAddVO.setDraft(true);
        int id = articleService.saveOrUpdateAndSetTagIdList(articleAddVO);
        Map<String, Object> map = new HashMap<>();
        map.put("articleId",id);
        return Result.success(map,"成功");
    }

    @GetMapping("/getArticleById")
    public Result getArticleById(@RequestParam("articleId") Integer articleId) {
        ArticleAddVO article = null;
        try {
            article = articleService.getArticleById(articleId);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_500,"系统错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("article",article);
        return Result.success(map,"文章加载成功");
    }

    @GetMapping("/listArticle")
    public Result getListArticle(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "articleTitle",defaultValue = "") String articleTitle
                                 ){
        pageNum = (pageNum - 1) * pageSize;
        System.out.println(pageNum+"   "+pageSize+"   "+articleTitle);
        List<ArticleDTO> articleList = articleService.getListArticle(pageNum, pageSize, articleTitle);
        Map<String, Object> map = new HashMap<>();
        map.put("articleList",articleList);
        map.put("total",articleService.count());
        return Result.success(map,"获取文章列表成功");
    }


    @DeleteMapping("/deleteArticleById")
    public Result deleteArticleById(@RequestParam("articleId") Integer articleId){
        try {
            articleService.removeById(articleId);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_500,"系统错误");
        }
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",articleId);
        try {
            articleTagMapper.delete(wrapper);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_500,"系统错误");
        }
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatchById(@RequestBody List<Integer> ids){
        if (ids.size() <= 0) {
            throw new ServiceException(ResultInfo.CODE_500,"系统错误");
        }
        for (Integer articleId : ids) {
            try {
                articleService.removeById(articleId);
            } catch (Exception e) {
                throw new ServiceException(ResultInfo.CODE_500,"系统错误");
            }
            QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
            wrapper.eq("article_id",articleId);
            try {
                articleTagMapper.delete(wrapper);
            } catch (Exception e) {
                throw new ServiceException(ResultInfo.CODE_500,"系统错误");
            }
        }
        return Result.success();
    }


}
