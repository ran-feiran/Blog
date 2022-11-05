package com.zhao.strategy.context;


import com.zhao.dto.ArticleSearchDTO;
import com.zhao.enums.SearchModeEnum;
import com.zhao.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 搜索策略上下文
 *
 * @author ran-feiran
 * @date 2022/09/23
 */
@Service
public class SearchStrategyContext {

    /**
     * 搜索模式
     */
    @Value("${search.mode}")
    private String searchMode;


    @Autowired
    private Map<String, SearchStrategy> searchStrategyMap;


    /**
     * 执行搜索策略
     *
     * @param keywords 关键字
     * @return {@link List}<{@link ArticleSearchDTO}>
     */
    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) throws IOException {
        return searchStrategyMap.get(SearchModeEnum.getStrategy(searchMode)).searchArticle(keywords);
    }
}
