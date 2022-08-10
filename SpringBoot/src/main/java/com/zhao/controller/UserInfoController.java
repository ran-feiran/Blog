package com.zhao.controller;

import com.zhao.api.BlogInfoService;
import com.zhao.dto.BlogHomeInfoDTO;
import com.zhao.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "博客信息模块")
@RequestMapping("/blogInfo")
public class UserInfoController {
    @Autowired
    private BlogInfoService blogInfoService;

    @ApiOperation(value = "获取博主的基本信息")
    @GetMapping("/getBlogInfo")
    public Result getBlogInfo(){
        BlogHomeInfoDTO blogInfo = blogInfoService.getBlogInfo();
        Map<String, Object> map = new HashMap<>();
        map.put("blogInfo", blogInfo);
        return Result.success(map, "");
    }
}
