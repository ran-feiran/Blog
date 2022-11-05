package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.BlogInfoBackService;
import com.zhao.api.BlogInfoService;
import com.zhao.dto.ArticleRecommendDTO;
import com.zhao.dto.BlogHomeInfoDTO;
import com.zhao.dto.BlogInfoBackDTO;
import com.zhao.dto.UserAreaDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.zhao.constant.OptTypeConst.REMOVE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

/**
 * 后台控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@Api(tags = "博客信息模块")
@Slf4j
public class BlogInfoBackController {

    @Autowired
    private BlogInfoBackService blogInfoBackService;

    @Autowired
    private BlogInfoService blogInfoService;

    @ApiOperation(value = "最近文章列表")
    @GetMapping("/blogInfo/getNewArticleList")
    public ResultStandby<List<ArticleRecommendDTO>> getNewArticleList() throws ExecutionException, InterruptedException {
        return success(blogInfoService.getNewArticleList(), SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取博客信息")
    @GetMapping("/blogInfo/getBlogInfo")
    public ResultStandby<BlogHomeInfoDTO> getBlogInfo() throws ExecutionException, InterruptedException {
        return success(blogInfoService.getBlogInfo(), SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取博客信息(后台)")
    @GetMapping("/blogInfo/getBlogInfoBack")
    public ResultStandby<BlogInfoBackDTO> getBlogInfoBack() {
        return success(blogInfoBackService.getBlogBackInfo(),SUCCESS.getDesc());
    }

    @ApiOperation(value = "用户地域分布")
    @GetMapping("/blogInfo/getUserArea")
    public ResultStandby<List<UserAreaDTO>> getUserArea(ConditionVO conditionVO) {
        return success(blogInfoBackService.listUserAreas(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "上传访客信息")
    @PostMapping("/blogInfo/report")
    public ResultStandby<?> reportVisitorInfo() {
        blogInfoBackService.report();
        return success(null,SUCCESS.getDesc());
    }

    @ApiOperation(value = "加载登录背景")
    @GetMapping("/blogInfo/loadLoginPage")
    public ResultStandby<String> loadLoginPage() {
        return success(blogInfoBackService.loadLoginPage(),SUCCESS.getDesc());
    }

}
