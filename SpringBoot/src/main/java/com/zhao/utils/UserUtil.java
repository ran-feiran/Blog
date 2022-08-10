package com.zhao.utils;

import com.zhao.auth.MyUserDetails;
import com.zhao.pojo.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * 获取用户工具包
 */
@Component
public class UserUtil {

    /**
     * 获取当前登录用户
     * @return
     */
    public static User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof UsernamePasswordAuthenticationToken) {
            MyUserDetails userAuth = (MyUserDetails) authentication.getPrincipal();
            return  userAuth.getUser();
        }
        return  null;
    }
}
