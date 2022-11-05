package com.zhao.api;

import com.zhao.dto.ArticleRecommendDTO;
import com.zhao.dto.BlogHomeInfoDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 博客信息服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface BlogInfoService {

    /**
     * 获取博客信息
     *
     * @return {@link BlogHomeInfoDTO}
     */
    BlogHomeInfoDTO getBlogInfo() throws ExecutionException, InterruptedException;

    /**
     * 获得新文章列表
     *
     * @return {@link List}<{@link ArticleRecommendDTO}>
     */
    List<ArticleRecommendDTO> getNewArticleList() throws ExecutionException, InterruptedException;
}
