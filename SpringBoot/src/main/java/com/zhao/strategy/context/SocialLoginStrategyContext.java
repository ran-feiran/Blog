package com.zhao.strategy.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhao.pojo.User;
import com.zhao.strategy.SocialLoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.zhao.enums.LoginTypeEnum.getName;


/**
 * 第三方登录策略上下文
 */
@Service
public class SocialLoginStrategyContext {


    @Autowired
    private Map<String, SocialLoginStrategy> socialLoginStrategyMap;

    /**
     * 执行登录策略
     *
     * @param data      数据
     * @param loginType 登录类型
     * @return {@link User}
     * @throws JsonProcessingException json处理异常
     */
    public User executeLoginStrategy(String data,Integer loginType) throws JsonProcessingException {
        return socialLoginStrategyMap.get(getName(loginType)).login(data,loginType);
    }

}
