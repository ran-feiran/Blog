package com.zhao.test;


import com.zhao.api.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
public class Test {

    @Autowired
    @Qualifier("elastic")
    RestHighLevelClient restHighLevelClient;

    @Autowired
    RedisService redisService;

    @org.junit.jupiter.api.Test
    void test() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("article");
        createIndexRequest.mapping("{\n" +
                "      \"properties\":{\n" +
                "         \"articleId\":{\n" +
                "          \"type\":\"integer\"\n" +
                "        },\n" +
                "        \"articleTitle\":{\n" +
                "          \"type\":\"text\",\n" +
                "           \"analyzer\": \"ik_max_word\",\n" +
                "           \"search_analyzer\": \"ik_max_word\"\n" +
                "        },\n" +
                "        \"articleContent\":{\n" +
                "          \"type\":\"text\",\n" +
                "          \"analyzer\": \"ik_max_word\",\n" +
                "          \"search_analyzer\": \"ik_max_word\"\n" +
                "        },\n" +
                "        \"status\":{\n" +
                "          \"type\":\"integer\"\n" +
                "        },\n" +
                "        \"isDelete\":{\n" +
                "          \"type\":\"integer\"\n" +
                "        }\n" +
                "      }\n" +
                "  }\n", XContentType.JSON);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
    }
}
