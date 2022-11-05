package com.zhao.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.FriendLinkService;
import com.zhao.dto.FriendLinkBackDTO;
import com.zhao.dto.PageDTO;
import com.zhao.mapper.FriendLinkMapper;
import com.zhao.pojo.FriendLink;
import com.zhao.utils.BeanCopyUtil;
import com.zhao.vo.ConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    @Override
    public PageDTO<FriendLinkBackDTO> getFriendLinkList(ConditionVO conditionVO) {
        Long count = friendLinkMapper.selectCount(null);
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        return new PageDTO<>(friendLinkMapper.getFriendLinkList(conditionVO), count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrEditFriendLink(FriendLinkBackDTO friendLink) {
        saveOrUpdate(BeanCopyUtil.copyObject(friendLink, FriendLink.class));
    }
}
