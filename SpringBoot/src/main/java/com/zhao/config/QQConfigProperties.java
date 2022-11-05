package com.zhao.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * qq配置属性
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "qq")
public class QQConfigProperties {
    /**
     * QQ appId
     */
    private String appId;

    /**
     * 校验token地址
     */
    private String checkTokenUrl;

    /**
     * QQ用户信息地址
     */
    private String userInfoUrl;
}
