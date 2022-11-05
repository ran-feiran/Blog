package com.zhao.handler;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.api.RedisService;
import com.zhao.api.UserLoginService;
import com.zhao.mapper.UserMapper;
import com.zhao.pojo.User;
import com.zhao.pojo.UserLogin;
import com.zhao.utils.IpUtil;
import com.zhao.utils.UserUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import static com.zhao.constant.CommonConst.CONTENT_TYPE;
import static com.zhao.constant.RedisPrefixConst.ARTICLE_USER_LIKE;
import static com.zhao.constant.RedisPrefixConst.COMMENT_USER_LIKE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.Result.success;


/**
 * 身份验证成功处理者
 * 登录成功后返回
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLoginService UserLoginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = updateUserLikeSet(Objects.requireNonNull(UserUtil.getLoginUser()));
        updateUserStatus(request);
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("roles", UserUtil.getLoginAuthorities());
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(success(map,SUCCESS.getDesc())));
    }

    private User updateUserLikeSet(User user) {
        Set<Object> commentLikeSet =  redisService
                .sMembers(COMMENT_USER_LIKE + user.getUserId()); // 用户评论点赞集合
        user.setCommentLikeSet(commentLikeSet);
        Set<Object> articleLikeSet =  redisService
                .sMembers(ARTICLE_USER_LIKE + user.getUserId()); // 用户文章点赞集合
        user.setArticleLikeSet(articleLikeSet);
        return user;
    }

    @Async
    void updateUserStatus(HttpServletRequest request) {
        String ipAddress = IpUtil.getIp(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        DateTime dateTime = new DateTime();
        User loginUser = UserUtil.getLoginUser();
        assert loginUser != null;
        loginUser
                .setIpAddress(ipAddress)
                .setIpSource(ipSource)
                .setLastLoginTime(dateTime);
        // 更新用户表
        userMapper.update(loginUser, new LambdaQueryWrapper<User>().eq(User::getUserId, loginUser.getUserId()));
        UserLogin userLogin =UserLogin.builder()
                                            .userLoginId(loginUser.getUserId())
                                            .nickname(loginUser.getNickname())
                                            .avatar(loginUser.getAvatar())
                                            .ipAddress(ipAddress)
                                            .ipSources(ipSource)
                                            .lastLoginTime(dateTime)
                                            .os(userAgent.getOperatingSystem().getName())
                                            .browser(userAgent.getBrowser().getName())
                                        .build();
        // 更新在线用户表
        UserLoginService.saveOrUpdate(userLogin,new LambdaQueryWrapper<UserLogin>().eq(UserLogin::getUserLoginId,loginUser.getUserId()));
    }
}
