package com.zhao.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.jdbc.StringUtils;
import com.zhao.api.RedisService;
import com.zhao.constant.DefaultUser;
import com.zhao.constant.RedisPrefixConst;
import com.zhao.result.ResultInfo;
import com.zhao.api.RoleService;
import com.zhao.api.UserService;
import com.zhao.dto.UserDTO;
import com.zhao.dto.UserSignalDTO;
import com.zhao.exception.div.ServiceException;
import com.zhao.mapper.RoleUserMapper;
import com.zhao.mapper.UserMapper;
import com.zhao.pojo.Role;
import com.zhao.pojo.RoleUser;
import com.zhao.pojo.User;
import com.zhao.vo.UserQueryVO;
import com.zhao.vo.UserRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleUserMapper roleUserMapper;

    @Autowired
    EmailMapperImpl emailMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<String> getUserRolesByUserId(Integer id) {
        return userMapper.getUserRolesByUserId(id);
    }

    @Override
    public List<UserDTO> getUserList(UserQueryVO userQueryVO) {
        return userMapper.getUserList(userQueryVO);
    }

    @Override
    public int updateUserById(UserDTO userDTO) {
        userDTO.setUpdateTime(new DateTime());
        return userMapper.updateUserById(userDTO);
    }

    @Override
    public int addUser(UserDTO userDTO) {
        userDTO.setCreateTime(new DateTime());
        int i = userMapper.addUser(userDTO);
        if (i <= 0) {
            throw new ServiceException(ResultInfo.CODE_600,"添加用户失败,或用户名重复");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",userDTO.getUsername());
        User user = userMapper.selectOne(wrapper);

        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(user.getUserId());
        roleUser.setRoleId(2);
        roleUserMapper.insert(roleUser);
        return i;
    }

    @Override
    public int updateSilenceById(boolean isSilence, Integer userId) {
        return userMapper.updateSilenceById(isSilence, userId);
    }

    @Override
    public List<UserSignalDTO> getUserRoleList(Integer current, Integer size, String nickname) {
        return userMapper.getUserRoleList(current, size, nickname);
    }

    @Override
    public int updateUserRole(UserSignalDTO userSignalDTO) {
        Integer userId = userSignalDTO.getUserId();
        QueryWrapper<RoleUser> wrapper = new QueryWrapper<>();
        QueryWrapper<Role> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_name", userSignalDTO.getRoleName());
        Role role = roleService.getOne(wrapper1);
        wrapper.eq("user_id",userId);
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(userId);
        roleUser.setRoleId(role.getRoleId());
        return roleUserMapper.update(roleUser, wrapper);
    }

    @Override
    public void sendCode(String email) {
        if (!checkEmail(email)) {
            throw new ServiceException(ResultInfo.CODE_600,"邮箱格式不正确");
        }
        // 发送验证码
        String code = emailMapper.sendEmail(email);
        //在把验证码放到redis 有效时间存为15分钟 这样就可以了
        redisService.set(RedisPrefixConst.USER_EMAIL_KEY + email, code, RedisPrefixConst.CODE_EXPIRE_TIME);
    }

    @Override
    public void registerUser(UserRegisterVO userRegisterVO) {
        String email = userRegisterVO.getEmail();
        String code = userRegisterVO.getCode();
        String o = (String) redisService.get(RedisPrefixConst.USER_EMAIL_KEY + email);

        if (StringUtils.isNullOrEmpty(o)) {
            throw new ServiceException(ResultInfo.CODE_600,"验证码已过期");
        }
        if (!code.equals(o)) {
            throw new ServiceException(ResultInfo.CODE_600,"验证码错误");
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(userRegisterVO.getUsername());
        //加密密码
        String password = new BCryptPasswordEncoder().encode(userRegisterVO.getPassword());
        user.setPassword(password);
        //设置默认头像
        user.setAvatar(DefaultUser.DEFAULT_AVATAR);
        //设置默认昵称
        user.setNickname(DefaultUser.NICKNAME+ UUID.randomUUID().
                toString().
                replaceAll("-","").
                replaceAll("[a-zA-Z]", String.valueOf(new Random().nextInt(10))).
                substring(0,9)
        );
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"用户名重复或邮箱已被他人占用");
        }
    }

    @Override
    public void forgetPassword(UserRegisterVO userRegisterVO) {
        String email = userRegisterVO.getEmail();
        String code = userRegisterVO.getCode();
        String o = (String) redisService.get(RedisPrefixConst.USER_EMAIL_KEY + email);

        if (StringUtils.isNullOrEmpty(o)) {
            throw new ServiceException(ResultInfo.CODE_600,"验证码已过期");
        }
        if (!code.equals(o)) {
            throw new ServiceException(ResultInfo.CODE_600,"验证码错误");
        }
        User user = new User();
        //加密密码
        String password = new BCryptPasswordEncoder().encode(userRegisterVO.getPassword());
        user.setPassword(password);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User one = userMapper.selectOne(wrapper);
        if (one != null) {
            userMapper.update(user,wrapper);
        } else {
            throw new ServiceException(ResultInfo.CODE_600,"用户不存在");
        }
    }


    /**
     * 检测邮箱是否合法
     * @param username 用户名
     * @return 合法状态
     */
    private boolean checkEmail(String username) {
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        //进行正则匹配
        return m.matches();
    }
}
