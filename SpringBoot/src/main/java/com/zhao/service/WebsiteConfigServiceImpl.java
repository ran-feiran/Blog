package com.zhao.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.RedisService;
import com.zhao.api.WebsiteConfigService;
import com.zhao.mapper.WebsiteConfigMapper;
import com.zhao.pojo.WebsiteConfig;
import com.zhao.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.zhao.constant.CommonConst.DEFAULT_CONFIG_ID;
import static com.zhao.constant.RedisPrefixConst.WEBSITE_CONFIG;

@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigMapper, WebsiteConfig> implements WebsiteConfigService {

    @Autowired
    private RedisService redisService;

    @Override
    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO config;
        // 获取缓存数据
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            config = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVO.class);
        } else {
            // 从数据库中加载
            String configInfo = getById(DEFAULT_CONFIG_ID).getConfig();
            config = JSON.parseObject(configInfo, WebsiteConfigVO.class);
            redisService.set(WEBSITE_CONFIG, configInfo);
        }
        return config;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO) {
        // 修改网站配置
        WebsiteConfig config = WebsiteConfig.builder()
                                            .id(DEFAULT_CONFIG_ID)
                                            .config(JSON.toJSONString(websiteConfigVO))
                                            .build();
        // 保存数据库
        saveOrUpdate(config);
        // 删除缓存
        redisService.del(WEBSITE_CONFIG);
    }

}
