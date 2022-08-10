package com.zhao.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.api.UserService;
import com.zhao.mapper.RoleMapper;
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


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    RoleUserMapper roleUserMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = userService.getOne(wrapper);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        System.out.println(user);
        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setUser(user);
        myUserDetails.setUsername(user.getUsername());
        myUserDetails.setPassword(user.getPassword());
        //System.out.println("{noop}"+user.getPassword());

        // 获取当前用户的角色
        List<String> roles = userService.getUserRolesByUserId(user.getUserId());

        // 无角色添加一个
        if (roles.size() <= 0 || roles == null){
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(user.getUserId());
            roleUser.setRoleId(2);
            roleUserMapper.insert(roleUser);
            roles = new ArrayList<>();
            roles.add("USER");
        }

        // 设置权限
        Set<GrantedAuthority> objects = new HashSet<>();
        SimpleGrantedAuthority authority = null;
        for (String role : roles) {
            authority = new SimpleGrantedAuthority(role);
            objects.add(authority);
        }
        myUserDetails.setAuthorities(objects);
        return myUserDetails;
    }
}
