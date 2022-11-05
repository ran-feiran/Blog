package com.zhao.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * es配置类
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {


    @Value("${elasticsearch.rest.host}")
    private String host;

    @Value("${elasticsearch.rest.port}")
    private String port;

    @Override
    @Bean(name = "elastic")
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration
                .builder()
                .connectedTo(host + ":" + port)  //===>与kibana客户端类型都是restful分格,都是连接9200端口
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
