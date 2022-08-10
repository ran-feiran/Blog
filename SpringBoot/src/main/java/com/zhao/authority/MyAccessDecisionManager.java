package com.zhao.authority;

import com.zhao.exception.div.MyAccessDeniedException;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 对设置的权限进行管理
 * 访问决策管理器
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    private final boolean supports = true;

    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 如果没有需要的权限信息直接放行
        if (configAttributes == null) {
            return;
        }

        // 匿名用户
        if (Objects.equals(authentication.getName(), "anonymousUser")) {
            List<String> collect = configAttributes.
                    stream().
                    map(ConfigAttribute::getAttribute).
                    collect(Collectors.toList());
            if (collect.contains("GUEST")) {
                return;
            }
        }

        // 获取登录用户的权限信息
        List<String> authorities = authentication.getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());
        for (ConfigAttribute configAttribute : configAttributes) {
            // 与我们后台通过路径和方法得到的权限进行比对
            if (authorities.contains(configAttribute.getAttribute())) {
                return;
            }
        }
        throw new MyAccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return supports;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return supports;
    }
}
