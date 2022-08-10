package com.zhao.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.pojo.UserLogin;

public interface UserLoginService extends IService<UserLogin> {

    IPage<UserLogin> getUserInfoList (Integer pageNum, Integer pageSize, String nickname);
}
