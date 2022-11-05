package com.zhao.utils;

import com.zhao.authentication.MyUserDetails;
import com.zhao.pojo.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 用户工具包
 */
@Component
public  final class UserUtil {

    /**
     * 获取当前登录用户
     *
     * @return {@link User}
     */
    public static User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            MyUserDetails userAuth = (MyUserDetails) authentication.getPrincipal();
            return  userAuth.getUser();
        }
        return null;
    }

    /**
     * 获得登录权限
     *
     * @return {@link List}<{@link String}>
     */
    public static List<String> getLoginAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            MyUserDetails userAuth = (MyUserDetails) authentication.getPrincipal();
            return  userAuth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        }
        return  new ArrayList<>();
    }
}
