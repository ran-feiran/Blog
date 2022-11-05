package com.zhao.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.AboutService;
import com.zhao.api.RedisService;
import com.zhao.mapper.AboutMapper;
import com.zhao.pojo.About;
import com.zhao.vo.BlogInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.zhao.constant.RedisPrefixConst.ABOUT;

@Service
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements AboutService {

    @Autowired
    private RedisService redisService;

    @Override
    public String getAbout() {
        return Optional.ofNullable(redisService.get(ABOUT)).orElse("").toString();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAbout(BlogInfoVO blogInfoVO) {
        redisService.set(ABOUT, blogInfoVO.getAboutContent());
    }
}

