package com.zhao.controller;


import com.mysql.jdbc.StringUtils;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.AboutService;
import com.zhao.exception.div.ServiceException;
import com.zhao.pojo.About;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AboutController {

    @Autowired
    AboutService aboutService;

    @GetMapping("/getAbout")
    public Result getAbout() {
        About about = null;
        try {
            about = aboutService.getById(1);
        } catch (Exception e) {
            throw  new ServiceException(ResultInfo.CODE_600, "失败");
        }
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isNullOrEmpty(about.getContent())) {
            map.put("content", about.getContent());
        }
        return Result.success(map, "亲爱的朋友,你好啊!");
    }

    @PostMapping("/updateAbout")
    @Transactional
    public Result updateAbout(@RequestBody About about) {
        System.out.println(about.getContent());
        try {
            aboutService.saveOrUpdate(about);
        } catch (Exception e) {
            throw  new ServiceException(ResultInfo.CODE_600, "更新失败");
        }
        return Result.success();
    }
}
