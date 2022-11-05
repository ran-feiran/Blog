package com.zhao.authentication;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhao.api.UserService;
import com.zhao.exception.ServiceException;
import com.zhao.mapper.RoleUserMapper;
import com.zhao.pojo.RoleUser;
import com.zhao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.zhao.enums.RoleEnum.USER;
import static com.zhao.enums.StatusCodeEnum.USERNAME_NOT_EXIST;


/**
 * 用户详细信息服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername,  username));
        if (user == null){
            throw new ServiceException(USERNAME_NOT_EXIST.getCode(),USERNAME_NOT_EXIST.getDesc());
        }
        // 获取当前用户的角色
        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setUser(user);
        myUserDetails.setUsername(user.getUsername());
        myUserDetails.setPassword(user.getPassword());
        List<String> roles = userService.getUserRolesByUserId(user.getUserId());
        // 无角色添加用户添加角色
        if (roles.size() == 0){
            roles = authorityRole(new RoleUser(), new ArrayList<>(), user);
        }
        // 设置权限
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 加入用户详情
        authorities.add(new SimpleGrantedAuthority(roles.get(0)));
        // 设置用户详情
        myUserDetails.setAuthorities(authorities);
        return myUserDetails;
    }

    private List<String> authorityRole(RoleUser roleUser, List<String> roles, User user) {
        // 更新用户角色表
        roleUser.setUserId(user.getUserId());
        roleUser.setRoleId(USER.getRoleId());
        roleUserMapper.insert(roleUser);
        // 授权给用户角色
        roles.add(USER.getLabel());
        return roles;
    }
}
