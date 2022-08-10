package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.UserLoginService;
import com.zhao.mapper.UserLoginMapper;
import com.zhao.pojo.UserLogin;
import org.springframework.stereotype.Service;

@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin> implements UserLoginService {

    @Override
    public IPage<UserLogin> getUserInfoList(Integer pageNum, Integer pageSize, String nickname) {
        Page<UserLogin> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UserLogin> wrapper=null;
        if(nickname != null && !nickname.equals("")){
            wrapper=new QueryWrapper<>();
            wrapper.like("nickname", nickname);
        }
        return page(page, wrapper);
    }
}
