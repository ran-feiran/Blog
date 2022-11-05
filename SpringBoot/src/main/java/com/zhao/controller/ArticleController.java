package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.ArticleService;
import com.zhao.dto.*;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ArticleTopVO;
import com.zhao.vo.ArticleVO;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.DeleteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


import static com.zhao.constant.OptTypeConst.*;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

/**
 * 文章控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章模块")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "文章搜索")
    @GetMapping("/search")
    public ResultStandby<List<ArticleSearchDTO>> getArticlesBySearch(ConditionVO conditionVO) throws IOException {
        return success(articleService.getArticlesBySearch(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "文章点赞")
    @OptLog(optType = SAVE)
    @PostMapping("/like/{articleId}")
    public ResultStandby<?> saveLikeArticle(@PathVariable("articleId") Integer articleId) {
        articleService.saveLikeArticle(articleId);
        return success();
    }

    @ApiOperation(value = "通过文章id获取文章")
    @GetMapping("/blog/{articleId}")
    public ResultStandby<ArticleBlogDTO> getArticleByIdBlog(@PathVariable("articleId") Integer articleId) throws Exception {
        return success(articleService.getArticleByIdBlog(articleId),SUCCESS.getDesc());
    }

    @ApiOperation(value = "文章归档")
    @GetMapping("/archives")
    public ResultStandby<PageDTO<ArchivesDTO>> getArchives(@RequestParam("current") Integer current) {
        return success(articleService.getArchives((current - 1) * 10),SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取文章列表")
    @GetMapping("/articles")
    public ResultStandby<List<ArticleBlogDTO>> listArticles(@RequestParam("current") Integer current) {
        return success(articleService.listArticles(current - 1),SUCCESS.getDesc());
    }

    @ApiOperation(value = "新增或更新文章")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/saveOrUpdateArticle")
    public ResultStandby<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return success(null,SUCCESS.getDesc());
    }

    @ApiOperation(value = "通过文章id获取文章(后台)")
    @GetMapping("/getArticleById")
    public ResultStandby<ArticleBackDTO> getArticleById(@RequestParam("articleId") Integer articleId) {
        return success(articleService.getArticleById(articleId),SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取文章列表(后台)")
    @GetMapping("/listArticle")
    public ResultStandby<PageDTO<ArticleDTO>> getListArticle(ConditionVO conditionVO){
        return success(articleService.getListArticle(conditionVO),SUCCESS.getDesc());
    }

    @ApiOperation(value = "逻辑删除文章")
    @OptLog(optType = UPDATE)
    @PutMapping("/del/batch")
    public ResultStandby<?> updateArticleDelete(@Valid @RequestBody DeleteVO deleteVO){
        articleService.updateArticleDelete(deleteVO);
        return success(null,SUCCESS.getDesc());
    }

    @ApiOperation(value = "物理删除文章")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/del/batch")
    public ResultStandby<?> deleteBatchById(@RequestBody List<Integer> ids){
        articleService.deleteBatchById(ids);
        return success();
    }

    @ApiOperation(value = "文章置顶")
    @OptLog(optType = UPDATE)
    @PutMapping("/top")
    public ResultStandby<?> updateTop(@Valid @RequestBody ArticleTopVO articleTopVO) {
        articleService.updateTop(articleTopVO);
        return success();
    }

}
