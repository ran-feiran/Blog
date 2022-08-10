package com.zhao.auth.handler;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.api.RedisService;
import com.zhao.api.UserLoginService;
import com.zhao.constant.RedisPrefixConst;
import com.zhao.pojo.UserLogin;
import com.zhao.result.Result;
import com.zhao.auth.MyUserDetails;
import com.zhao.pojo.User;
import com.zhao.utils.IpUtil;
import com.zhao.utils.UserUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 登录成功后返回
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserLoginService userLoginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object principal = authentication.getPrincipal();  // myuserdetailsservice返回的myuserdetails
        User user = null;
        List<String> roles = null;
        if (principal instanceof UserDetails) {
            MyUserDetails userDetails = (MyUserDetails) principal;
            user = userDetails.getUser();
            roles = new ArrayList<>();
            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                roles.add(authority.getAuthority());
            }
        }
        assert user != null;
        String commentLikeKey = RedisPrefixConst.COMMENT_USER_LIKE + user.getUserId();
        String articleLikeKey = RedisPrefixConst.ARTICLE_USER_LIKE + user.getUserId();
        Set<Object> commentLikeSet =  redisService.sMembers(commentLikeKey); // 用户评论点赞集合
        Set<Object> articleLikeSet =  redisService.sMembers(articleLikeKey); // 用户文章点赞集合
        user.setCommentLikeSet(commentLikeSet);
        user.setArticleLikeSet(articleLikeSet);
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("roles", roles);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                mapper.writeValueAsString(
                        Result.success(map,"登陆成功")
                )
        );
        // 更新用户ip，最近登录时间
        UserLogin userLogin = new UserLogin();
        String ipAddress = IpUtil.getIp(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAgent userAgent = IpUtil.getUserAgent(request);
//        userAgent.toString();
        userLogin.
                setUserLoginId(UserUtil.getLoginUser().getUserId()).
                setIpSources(ipSource).
                setIpAddress(ipAddress).
                setNickname(UserUtil.getLoginUser().getNickname()).
                setLoginType(userAgent.toString()).
                setAvatar(UserUtil.getLoginUser().getAvatar()).
                setLoginTime(UserUtil.getLoginUser().getCreateTime()).
                setLastLoginTime(new DateTime()); // 设置最近登录时间
        System.out.println("1111{"+userAgent+"}111");
        userLoginService.saveOrUpdate(userLogin);
    }

}
