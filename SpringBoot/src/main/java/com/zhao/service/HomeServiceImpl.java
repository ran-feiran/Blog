package com.zhao.service;

import com.zhao.api.*;
import com.zhao.constant.RedisPrefixConst;
import com.zhao.dto.HomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    ArticleService articleService;

    @Override
    public HomeDTO getHomeInfo() {
        HomeDTO homeDTO = new HomeDTO();
        long userCount = userService.count();
        long messageCount = messageService.count();
        long articleCount = articleService.count();
        redisService.incr(RedisPrefixConst.BLOG_VIEWS_COUNT,1L);
        Integer viewsCount =(Integer) redisService.get(RedisPrefixConst.BLOG_VIEWS_COUNT);
        homeDTO.
                setUserCount(userCount).
                setArticleCount(articleCount).
                setMessageCount(messageCount).
                setViewsCount(viewsCount);
        return homeDTO;
    }
}
