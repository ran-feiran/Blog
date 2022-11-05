package com.zhao.authority;

import com.zhao.dto.ApiPermissionDTO;
import com.zhao.mapper.ApiMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.zhao.constant.CommonConst.*;
import static com.zhao.enums.RoleEnum.GUEST;


/**
 * 过滤器调用安全元数据来源
 *
 * @author ran-feiran
 * @date 2022/10/19
 */
@Component
@Slf4j
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ApiMapper apiMapper;

    /**
     * 所有资源权限
     */
    private static List<ApiPermissionDTO> allApiPermission;

    /**
     * 加载资源角色信息
     */
    @PostConstruct
    private void loadDataSource() {
        allApiPermission = apiMapper.getAllApiPermission();
    }

    /**
     * 清空接口角色信息
     */
    public void clearDataSource() {
        allApiPermission = null;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 修改接口角色关系后重新加载
        if (CollectionUtils.isEmpty(allApiPermission)) {
            this.loadDataSource();
        }
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        // 先匹配路径
        String url = request.getRequestURI();
        // 再匹配方法
        String method = request.getMethod();
        // 创建路径匹配器
        AntPathMatcher matcher = new AntPathMatcher();
        // 获取接口角色信息，若为匿名接口则放行，若无对应角色则禁止
        log.info("====================SpringSecurity执行前====================路径：{},方法：{}",url, method);
        for (ApiPermissionDTO apiPermissionDTO : allApiPermission) {
            if (matcher.match(apiPermissionDTO.getUrl(),url) &&
                    apiPermissionDTO.getRequestMethod().equals(method)) {
                List<String> roleList = apiPermissionDTO.getRoleList();
                if (apiPermissionDTO.getIsAnonymous() == TRUE) {
                    roleList.add(GUEST.getLabel());
                }
                if (CollectionUtils.isEmpty(roleList)) {
                    return SecurityConfig.createList(DISABLE);
                }
                return SecurityConfig.createList(roleList.toArray(new String[]{}));
            }
        }
        return SecurityConfig.createList(UNKNOWN);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
