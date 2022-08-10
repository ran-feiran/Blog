package com.zhao.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.AboutService;
import com.zhao.mapper.AboutMapper;
import com.zhao.pojo.About;
import org.springframework.stereotype.Service;

@Service
public class AboutServiceImpl extends ServiceImpl<AboutMapper, About> implements AboutService {

}

