package com.zhao.strategy.Impl;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.config.QQConfigProperties;
import com.zhao.constant.SocialLoginConst;
import com.zhao.dto.QQTokenDTO;
import com.zhao.dto.QQUserInfoDTO;
import com.zhao.dto.SocialTokenDTO;
import com.zhao.dto.SocialUserInfoDTO;
import com.zhao.exception.ServiceException;
import com.zhao.vo.QQLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.zhao.enums.StatusCodeEnum.QQ_LOGIN_ERROR;

/**
 * qq登录策略实现
 */
@Service("qqLoginStrategyImpl")
public class QQLoginStrategyImpl extends AbstractSocialLoginStrategyImpl{

    @Autowired
    private QQConfigProperties qqConfigProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SocialTokenDTO getSocialToken(String data,Integer loginType) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        QQLoginVO qqLoginVO = mapper.readValue(data, QQLoginVO.class);
        // 校验QQ token信息
        checkQQToken(qqLoginVO);
        // 返回Token信息
        return SocialTokenDTO.
                builder().
                openId(qqLoginVO.getOpenId()).
                accessToken(qqLoginVO.getAccessToken()).
                loginType(loginType).
                build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO) {
        // 定义请求参数
        Map<String, String> formData = new HashMap<>(3);
        formData.put(SocialLoginConst.QQ_OPEN_ID, socialTokenDTO.getOpenId());
        formData.put(SocialLoginConst.ACCESS_TOKEN, socialTokenDTO.getAccessToken());
        formData.put(SocialLoginConst.OAUTH_CONSUMER_KEY, qqConfigProperties.getAppId());
        // 获取QQ返回的用户信息
        QQUserInfoDTO qqUserInfoDTO = JSON.parseObject(
                restTemplate.getForObject(qqConfigProperties.getUserInfoUrl(), String.class, formData),
                QQUserInfoDTO.class);
        // 返回用户信息
        return SocialUserInfoDTO.
                builder().
                nickname(qqUserInfoDTO.getNickname()).
                avatar(qqUserInfoDTO.getFigureurl_qq_1()).
                build();
    }

    /**
     * 校验qq token信息
     * @param qqLoginVO qq登录信息
     */
    private void checkQQToken(QQLoginVO qqLoginVO) {
        // 根据token获取qq openId信息
        Map<String, String> qqData = new HashMap<>(1);
        qqData.put(SocialLoginConst.ACCESS_TOKEN, qqLoginVO.getAccessToken());
        try {
            String result = restTemplate.getForObject(qqConfigProperties.getCheckTokenUrl(), String.class, qqData);
            // 这一步至关重要
            result = result.replaceAll("[(); ]", "").replaceAll("callback","");
            QQTokenDTO qqTokenDTO = new ObjectMapper().readValue(Objects.requireNonNull(result), QQTokenDTO.class);
            // 判断openId是否一致
            if (!qqLoginVO.getOpenId().equals(qqTokenDTO.getOpenid())) {
                throw new ServiceException(QQ_LOGIN_ERROR.getCode(), QQ_LOGIN_ERROR.getDesc());
            }
        } catch (Exception e) {
            throw new ServiceException(QQ_LOGIN_ERROR.getCode(), QQ_LOGIN_ERROR.getDesc());
        }
    }
}
