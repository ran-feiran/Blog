package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.pojo.About;
import com.zhao.vo.BlogInfoVO;

/**
 * about服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface AboutService extends IService<About> {

    /**
     * 获得关于我
     *
     * @return {@link String}
     */
    String getAbout();

    /**
     * 更新关于我
     *
     * @param blogInfoVO 博客信息签证官
     */
    void updateAbout(BlogInfoVO blogInfoVO);
}
