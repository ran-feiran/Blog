package com.zhao.strategy.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.zhao.dto.ArticleSearchDTO;
import com.zhao.strategy.SearchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zhao.constant.CommonConst.*;
import static com.zhao.enums.ArticleStatusEnum.PUBLIC;
import static com.zhao.enums.SearchConstEnum.ARTICLE;

/**
 * es搜索策略实现
 *
 * @author ran-feiran
 * @date 2022/09/24
 */
@Service("esSearchStrategyImpl")
@Slf4j
public class EsSearchStrategyImpl implements SearchStrategy {

    @Autowired
    @Qualifier("elastic")
    private RestHighLevelClient restHighLevelClient;

    @Override
    public List<ArticleSearchDTO> searchArticle(String keywords) throws IOException {
        if (StringUtils.isNullOrEmpty(keywords)) {
            return new ArrayList<>();
        }
        log.info("检索文章开始，关键字为：{}",keywords);
        return search(buildQuery(keywords));
    }


    /**
     * 搜索文章构造
     *
     * @param keywords 关键字
     * @return es条件构造器
     */
    private SearchSourceBuilder buildQuery(String keywords) {
        // 构建搜索源构造器
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 构建bool查询构造者
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 根据关键词搜索文章标题或内容
        boolQueryBuilder.must(
                QueryBuilders.boolQuery().
                                should(QueryBuilders.matchQuery("articleTitle",keywords)).
                                should(QueryBuilders.matchQuery("articleContent",keywords))).
                        must(QueryBuilders.termQuery("isDelete",FALSE)).
                        must(QueryBuilders.termQuery("status",PUBLIC.getStatus()));
        return builder.query(boolQueryBuilder);
    }

    /**
     * 文章搜索结果高亮
     *
     * @param searchSourceBuilder es条件构造器
     * @return 搜索结果
     */
    private List<ArticleSearchDTO> search(SearchSourceBuilder searchSourceBuilder) throws IOException {
        List<ArticleSearchDTO> articleList = new ArrayList<>();
        // 建立搜索请求
        SearchRequest searchRequest = new SearchRequest(ARTICLE.getIndex());
        // 添加文章内容与标题高亮
        HighlightBuilder contentField = new HighlightBuilder().
                requireFieldMatch(BOOL_FALSE).
                field("articleContent").
                field("articleTitle").
                preTags(PRE_TAG).
                postTags(POST_TAG);
        searchSourceBuilder.highlighter(contentField);
        // 搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (SearchHit hit : hits) {
                ArticleSearchDTO articleSearchDTO = mapper.readValue(hit.getSourceAsString(), ArticleSearchDTO.class);
                log.info("搜索结果：{}", hit.getSourceAsString());
                // 获取每条被击中的高亮属性
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                // 获取文章标题高亮数据
                if (highlightFields.containsKey("articleTitle")) {
                    // 替换标题数据
                    articleSearchDTO.setArticleTitle(
                            highlightFields.get("articleTitle").fragments()[0].toString()
                    );
                }
                // 获取文章内容高亮数据
                if (highlightFields.containsKey("articleContent")) {
                    // 替换内容数据
                    articleSearchDTO.setArticleContent(
                            highlightFields.get("articleContent").fragments()[0].toString()
                    );
                }
                log.info("文章高亮展示：{}", articleSearchDTO.toString());
                articleList.add(articleSearchDTO);
            }
            return articleList;
        } catch (Exception e) {
            log.error("堆栈请求：{}",e.getMessage());
        }
        return articleList;
    }
}
