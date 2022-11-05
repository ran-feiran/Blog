package com.zhao.strategy;

import com.zhao.dto.ArticleSearchDTO;

import java.io.IOException;
import java.util.List;

/**
 * 搜索策略
 *
 * @author ran-feiran
 * @date 2022/09/23
 */
public interface SearchStrategy {

    /**
     * 搜索文章
     *
     * @param keywords 关键字
     * @return {@link List}<{@link ArticleSearchDTO}>
     */
    List<ArticleSearchDTO> searchArticle(String keywords) throws IOException;

}
