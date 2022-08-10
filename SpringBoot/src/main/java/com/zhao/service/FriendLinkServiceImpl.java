package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.jdbc.StringUtils;
import com.zhao.api.FriendLinkService;
import com.zhao.mapper.FriendLinkMapper;
import com.zhao.pojo.FriendLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Autowired
    FriendLinkMapper friendLinkMapper;

    @Override
    public IPage<FriendLink> getFriendLinkList(Integer current, Integer size, String nickname) {
        Page<FriendLink> page = new Page<>(current, size);
        QueryWrapper<FriendLink> wrapper = null;
        if (!StringUtils.isNullOrEmpty(nickname)) {
            wrapper = new QueryWrapper<>();
            wrapper.like("nickname", nickname);
        }
        return friendLinkMapper.selectPage(page, wrapper);
    }
}
