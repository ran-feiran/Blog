package com.zhao.controller;


import com.zhao.annotations.AccessLimit;
import com.zhao.annotations.OptLog;
import com.zhao.api.WebsiteConfigService;
import com.zhao.result.ResultStandby;
import com.zhao.vo.WebsiteConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.zhao.constant.OptTypeConst.UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

@RestController
@Api(tags = "网站配置模块")
public class WebsiteConfigController {

    @Autowired
    private WebsiteConfigService websiteConfigService;

    @ApiOperation(value = "获取网站配置")
    @GetMapping("/website/getWebsiteConfig")
    public ResultStandby<WebsiteConfigVO> getWebsiteConfig() {
        return success(websiteConfigService.getWebsiteConfig(), SUCCESS.getDesc());
    }

    @ApiOperation(value = "更新网站配置")
    @AccessLimit(seconds = 60 * 15, maxCount = 1, desc = "请求过于频繁，请稍候再试")
    @OptLog(optType = UPDATE)
    @PutMapping("/website/updateWebsiteConfig")
    public ResultStandby<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        websiteConfigService.updateWebsiteConfig(websiteConfigVO);
        return success();
    }

}
