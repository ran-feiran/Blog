package com.zhao.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.pojo.FriendLink;

public interface FriendLinkService extends IService<FriendLink> {


    IPage<FriendLink> getFriendLinkList(Integer current, Integer size, String nickname);
}
