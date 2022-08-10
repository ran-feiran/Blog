package com.zhao.config;

import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        DataSize dataSize = DataSize.ofBytes(104857600L);// 10MB
        DataSize maxDataSize = DataSize.ofBytes(104857600L);// 100MB
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个数据大小
        factory.setMaxFileSize(dataSize); // KB,MB
        // 总上传数据大小
        factory.setMaxRequestSize(maxDataSize);
        return factory.createMultipartConfig();
    }
}
