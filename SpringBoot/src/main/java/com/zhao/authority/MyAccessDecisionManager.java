package com.zhao.authority;

import com.zhao.exception.MyAccessDeniedException;
import lombok.extern.slf4j.Slf4j;
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

import static com.zhao.constant.CommonConst.ANONYMOUS_USER;
import static com.zhao.constant.CommonConst.UNKNOWN;
import static com.zhao.enums.RoleEnum.GUEST;
import static com.zhao.enums.StatusCodeEnum.AUTHORIZED;
import static com.zhao.enums.StatusCodeEnum.NO_LOGIN;


/**
 * 访问决策管理器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Component
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        // 当前接口拥有的所有角色
        List<String> roleList = configAttributes.stream()
                .map(ConfigAttribute::getAttribute).collect(Collectors.toList());
        if (roleList.contains(UNKNOWN)) {
            throw new MyAccessDeniedException(NO_LOGIN.getDesc());
        }
        log.info("====================SpringSecurity执行中====================");
        accessDecisionHandler(authentication, roleList);
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private void accessDecisionHandler(Authentication authentication, List<String> roleList) {
        // 判断是否匿名用户
        if (Objects.equals(authentication.getName(), ANONYMOUS_USER)) {
            if (roleList.contains(GUEST.getLabel())) {
                return;
            }
        } else {
            // 获取登录用户的权限信息
            List<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            for (String item : roleList) {
                if (authorities.contains(item)) {
                    return;
                }
            }
        }
        throw new MyAccessDeniedException(AUTHORIZED.getDesc());
    }
}
