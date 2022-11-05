package com.zhao.strategy.Impl;

import com.zhao.dto.ArticleSearchDTO;
import com.zhao.strategy.SearchStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("mysqlSearchStrategyImpl")
public class MysqlSearchStrategyImpl implements SearchStrategy {

    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) {
        return new ArrayList<>();
    }
}
