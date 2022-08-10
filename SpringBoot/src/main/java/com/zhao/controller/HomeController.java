package com.zhao.controller;


import com.zhao.api.HomeService;
import com.zhao.dto.HomeDTO;
import com.zhao.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {


    @Autowired
    private HomeService homeService;

    @GetMapping("/home/getHomeInfo")
    public Result getHomeInfo() {
        HomeDTO homeInfo = homeService.getHomeInfo();
        Map<String, Object> map = new HashMap<>();
        map.put("data",homeInfo);
        return Result.success(map,"");
    }
}
