package com.zhao.consumer;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.dto.ArticleSearchDTO;
import com.zhao.dto.MaxwellDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.zhao.constant.MQPrefixConst.FANOUT_MAXWELL_QUEUE;
import static com.zhao.enums.SearchConstEnum.ARTICLE;

/**
 * maxwell监听数据
 *
 * @author ran-feiran
 * @date 2022/09/25
 */
@Component
@Slf4j
@RabbitListener(queues = FANOUT_MAXWELL_QUEUE)
public class MaxWellConsumer {

    @Autowired
    @Qualifier("elastic")
    private RestHighLevelClient restHighLevelClient;

    @RabbitHandler
    public void process(byte[] data) throws Exception {
        log.info("Maxwell抓取数据前---------");
        // 获取监听信息
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        // 获取文章数据
        ArticleSearchDTO article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), ArticleSearchDTO.class);
        log.info("文章数据展示:{}", article.toString());
        log.info("Maxwell抓取数据中:{}", JSON.toJSONString(maxwellDataDTO.getData()));
        log.info("Maxwell抓取数据后---------");
        // 判断操作类型
        log.info("当前数据类型为:{}",maxwellDataDTO.getType());
        ObjectMapper mapper = new ObjectMapper();
        switch (maxwellDataDTO.getType()) {
            case "insert":
                insertArticle(article,mapper);
                break;
            case "update":
                updateArticle(article,mapper);
                break;
            case "delete":
                deleteArticle(article);
                break;
            default:
                log.info("未知的类型");
                break;
        }
    }

    private void insertArticle(ArticleSearchDTO articleSearchDTO, ObjectMapper mapper) throws JsonProcessingException {
        IndexRequest indexRequest = new IndexRequest(ARTICLE.getIndex(),ARTICLE.getType(),articleSearchDTO.getArticleId().toString());
        indexRequest.
                    source(mapper.writeValueAsString(articleSearchDTO),XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.info("文章拉取异常：{}", e.getMessage());
        }
        assert indexResponse != null;
        log.info("文章拉取完成, 状态：{}", indexResponse.status());
    }

    private void updateArticle(ArticleSearchDTO articleSearchDTO, ObjectMapper mapper) throws JsonProcessingException {
        UpdateRequest updateRequest = new UpdateRequest(ARTICLE.getIndex(), ARTICLE.getType(), articleSearchDTO.getArticleId().toString());
        updateRequest.
                    doc(mapper.writeValueAsString(articleSearchDTO), XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.info("文章更新异常：{}", e.getMessage());
        }
        assert updateResponse != null;
        log.info("文章更新完成, 状态：{}", updateResponse.status());
    }

    private void deleteArticle(ArticleSearchDTO articleSearchDTO) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest( ARTICLE.getIndex(), ARTICLE.getType(), articleSearchDTO.getArticleId().toString());
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.info("文章删除异常：{}", e.getMessage());
        }
        assert deleteResponse != null;
        log.info("文章删除完成, 状态：{}", deleteResponse.status());
    }

}
