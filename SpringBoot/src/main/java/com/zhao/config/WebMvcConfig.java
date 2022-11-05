package com.zhao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 4 对接口配置跨域设置
                .allowCredentials(true)
                .allowedMethods("*")  // 3 设置访问源请求方法
                .allowedHeaders("*")  // 2 设置访问源请求头
                .allowedOrigins("*")  // 1 设置访问源地址
                .maxAge(MAX_AGE);
    }
}
