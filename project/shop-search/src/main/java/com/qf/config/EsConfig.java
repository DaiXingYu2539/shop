package com.qf.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

    //@Value("${es.host}")
    private String esHost="192.168.20.136";//

    //@Value("${es.port}")
    private Integer esPort=9200;

    @Bean(value = "resthighLevelClient")
    public RestHighLevelClient restClinet() {
        HttpHost httpHost = new HttpHost(esHost, esPort);
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);

        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
        return client;
    }
}
