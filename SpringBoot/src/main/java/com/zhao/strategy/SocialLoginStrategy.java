package com.zhao.strategy;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhao.pojo.User;

/**
 *
 * 第三方登录策略
 *
 * @author ran-feiran
 * @date 2022/09/23
 */
public interface SocialLoginStrategy {

    /**
     * 登录
     * @param data
     * @return
     */
    User login(String data,Integer loginType) throws JsonProcessingException;
}
