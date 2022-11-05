package com.zhao.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.pojo.WebsiteConfig;
import com.zhao.vo.WebsiteConfigVO;

public interface WebsiteConfigService extends IService<WebsiteConfig> {

    /**
     * 得到网站配置
     *
     * @return {@link WebsiteConfigVO}
     */
    WebsiteConfigVO getWebsiteConfig();

    /**
     * 更新网站配置
     *
     * @param websiteConfigVO 网站配置签证官
     */
    void updateWebsiteConfig(WebsiteConfigVO websiteConfigVO);
}
