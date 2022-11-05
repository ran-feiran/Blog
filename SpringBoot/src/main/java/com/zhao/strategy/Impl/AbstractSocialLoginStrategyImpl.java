package com.zhao.strategy.Impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhao.api.RedisService;
import com.zhao.api.UserLoginService;
import com.zhao.authentication.MyUserDetails;
import com.zhao.dto.SocialTokenDTO;
import com.zhao.dto.SocialUserInfoDTO;
import com.zhao.enums.LoginTypeEnum;
import com.zhao.mapper.RoleUserMapper;
import com.zhao.mapper.UserMapper;
import com.zhao.pojo.RoleUser;
import com.zhao.pojo.User;
import com.zhao.pojo.UserLogin;
import com.zhao.strategy.SocialLoginStrategy;
import com.zhao.utils.IpUtil;
import com.zhao.utils.UserUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.zhao.constant.RedisPrefixConst.ARTICLE_USER_LIKE;
import static com.zhao.constant.RedisPrefixConst.COMMENT_USER_LIKE;
import static com.zhao.enums.LoginTypeEnum.QQ;
import static com.zhao.enums.RoleEnum.USER;


/**
 * 抽象社交登录策略实现
 *
 *
 * @author ran-feiran
 * @date 2022/09/24
 */
@Service
public abstract class AbstractSocialLoginStrategyImpl implements SocialLoginStrategy {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public User login(String data, Integer loginType) throws JsonProcessingException {
        // 获取第三方token信息
        SocialTokenDTO socialToken = getSocialToken(data, loginType);
        // 判断用户是否注册过
        User user = getUser(socialToken);
        if (Objects.nonNull(user)) {
            // 返回数据库用户信息 -- 补充文章点赞，评论点赞
            user = getUserDetail(user);
            // 更新用户登录信息
            updateUserStatus(request,user);
        } else {
            // 获取第三方用户信息，保存到数据库返回
            user = saveUserDetail(socialToken);
            // 更新用户登录信息
            updateUserStatus(request,user);
        }
        // 获取用户角色
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(USER.getLabel()));
        // 将登录信息放入springSecurity管理
        MyUserDetails myUserDetails = BeanCopy(user,authorities);
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(loginToken);
        return user;
    }


    /**
     * 获取第三方token信息
     * @param data 数据
     * @return {@link SocialTokenDTO} 第三方token信息
     */
    public abstract SocialTokenDTO getSocialToken(String data, Integer loginType) throws JsonProcessingException;


    /**
     * 获取第三方用户信息
     * @param socialTokenDTO 第三方token信息
     * @return {@link SocialUserInfoDTO} 第三方用户信息
     */
    public abstract SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO) throws JsonProcessingException;


    /**
     * 获取用户账号
     * @return {@link User} 用户账号
     */
    private User getUser(SocialTokenDTO socialTokenDTO) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, socialTokenDTO.getOpenId()));
    }

    private MyUserDetails BeanCopy(User user, Set<GrantedAuthority> authorities) {
        return MyUserDetails.
                builder().
                user(user).
                username(user.getUsername()).
                password(user.getPassword()).
                authorities(authorities).
                accountNonExpired(true).
                accountNonLocked(true).
                enabled(true).
                credentialsNonExpired(true).
                build();
    }

    private User getUserDetail(User user) {
        Set<Object> commentLikeSet =  redisService
                .sMembers(COMMENT_USER_LIKE + user.getUserId()); // 用户评论点赞集合
        user.setCommentLikeSet(commentLikeSet);
        Set<Object> articleLikeSet =  redisService
                .sMembers(ARTICLE_USER_LIKE + user.getUserId()); // 用户文章点赞集合
        user.setArticleLikeSet(articleLikeSet);
        return user;
    }

    private User saveUserDetail(SocialTokenDTO socialToken) throws JsonProcessingException {
        // 获取第三方用户信息
        SocialUserInfoDTO socialUserInfo = getSocialUserInfo(socialToken);
        // 保存用户信息
        User user = new User();
        user
                .setNickname(socialUserInfo.getNickname())
                .setAvatar(socialUserInfo.getAvatar());
        userMapper.insert(user);
        // 保存账号信息
        user
                .setLoginType(socialToken.getLoginType())
                .setUsername(socialToken.getOpenId())
                .setPassword(socialToken.getAccessToken());
        userMapper.update(user,new LambdaQueryWrapper<User>().eq(User::getUserId, user.getUserId()));
        // 绑定角色
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(user.getUserId());
        roleUser.setRoleId(USER.getRoleId());
        roleUserMapper.insert(roleUser);
        return user;
    }


    @Async
    void updateUserStatus(HttpServletRequest request, User user) {
        String ipAddress = IpUtil.getIp(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        DateTime dateTime = new DateTime();
        user
                .setIpAddress(ipAddress)
                .setIpSource(ipSource)
                .setLastLoginTime(dateTime);
        // 更新用户表
        userMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUserId, user.getUserId()));
        UserLogin userLogin =UserLogin.builder()
                .userLoginId(user.getUserId())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .ipAddress(ipAddress)
                .ipSources(ipSource)
                .lastLoginTime(dateTime)
                .os(userAgent.getOperatingSystem().getName())
                .browser(userAgent.getBrowser().getName())
                .build();
        // 更新在线用户表
        userLoginService.saveOrUpdate(userLogin,new LambdaQueryWrapper<UserLogin>().eq(UserLogin::getUserLoginId,user.getUserId()));
    }


}
