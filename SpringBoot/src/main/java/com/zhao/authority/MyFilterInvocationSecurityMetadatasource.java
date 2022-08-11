package com.zhao.authority;

import com.zhao.api.ApiService;
import com.zhao.dto.ApiPermissionDTO;
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
import java.util.*;


/**
 * 设置权限，为路径和方法
 */
@Component
@Slf4j
public class MyFilterInvocationSecurityMetadatasource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ApiService apiService;

    private static List<ApiPermissionDTO> allApiPermission;

    /**
     * 加载资源角色信息
     */
    @PostConstruct
    private void loadDataSource() {
        allApiPermission = apiService.getAllApiPermission();
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
        AntPathMatcher matcher = new AntPathMatcher();
        TreeSet<String> objects = new TreeSet<>();
        for (ApiPermissionDTO apiPermissionDTO : allApiPermission) {
            if (matcher.match(apiPermissionDTO.getUrl(),url) &&
                    (apiPermissionDTO.getRequestMethod().equals(method) ||
                            apiPermissionDTO.getRequestMethod().equals("ALL"))) {
                List<String> roleList = apiPermissionDTO.getRoleList();
                objects.addAll(roleList);
//                System.out.println(Arrays.toString(objects.toArray(new String[]{})) +"11111");
            }
        }
        if (objects.size() <= 0) {
            return null;
        }
        if (url.startsWith("/role/saveRolePermissionList/") && method.equals("POST")) {
            this.clearDataSource();
        }
        return SecurityConfig.createList(objects.toArray(new String[]{}));
//        return SecurityConfig.createList("ADMIN","TEST","USER","GUEST");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);    }
}
