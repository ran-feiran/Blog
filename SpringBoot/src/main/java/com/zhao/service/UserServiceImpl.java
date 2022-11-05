package com.zhao.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.api.RedisService;
import com.zhao.api.UserService;
import com.zhao.api.WebsiteConfigService;
import com.zhao.dto.EmailDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.UserDTO;
import com.zhao.dto.UserSignalDTO;
import com.zhao.enums.FilePathEnum;
import com.zhao.exception.ServiceException;
import com.zhao.mapper.RoleUserMapper;
import com.zhao.mapper.UserMapper;
import com.zhao.pojo.RoleUser;
import com.zhao.pojo.User;
import com.zhao.strategy.context.SocialLoginStrategyContext;
import com.zhao.strategy.context.UploadStrategyContext;
import com.zhao.utils.UserUtil;
import com.zhao.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.zhao.constant.CommonConst.*;
import static com.zhao.constant.DefaultUser.NICKNAME;
import static com.zhao.constant.MQPrefixConst.FANOUT_EMAIL_EXCHANGE;
import static com.zhao.constant.RedisPrefixConst.CODE_EXPIRE_TIME;
import static com.zhao.constant.RedisPrefixConst.USER_EMAIL_KEY;
import static com.zhao.enums.LoginTypeEnum.EMAIL;
import static com.zhao.enums.LoginTypeEnum.QQ;
import static com.zhao.enums.StatusCodeEnum.FAIL;
import static com.zhao.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.zhao.utils.CommonUtil.*;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SocialLoginStrategyContext socialLoginStrategyContext;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private WebsiteConfigService websiteConfigService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<String> getUserRolesByUserId(Integer id) {
        return userMapper.getUserRolesByUserId(id);
    }

    @Override
    public PageDTO<UserDTO> getUserList(ConditionVO conditionVO) {
        // 查询所有的用户
        Long count = userMapper.selectCount(null);
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>() , 0);
        }
        // 查询所有的用户
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        return new PageDTO<>(userMapper.getUserList(conditionVO), count);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSilenceById(UserSilenceVO userSilenceVO) {
        userMapper.updateSilenceById(userSilenceVO.getIsSilence(), userSilenceVO.getUserId());
        User loginUser = UserUtil.getLoginUser();
        assert loginUser != null;
        loginUser.setIsSilence(userSilenceVO.getIsSilence());
    }

    @Override
    public List<UserSignalDTO> getUserRoleList(Integer current, Integer size, String nickname) {
        return userMapper.getUserRoleList(current, size, nickname);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        // 更新用户角色和昵称
        User user = User
                        .builder()
                        .userId(userRoleVO.getUserId())
                        .nickname(userRoleVO.getNickname())
                        .build();
        userMapper.updateById(user);
        // 添加角色
        if (userRoleVO.getRoleIdList().size() > 1) {
            throw new ServiceException(SYSTEM_ERROR.getCode(),SYSTEM_ERROR.getDesc());
        }
        Integer roleId = userRoleVO.getRoleIdList().get(0);
        RoleUser roleUser = RoleUser
                                .builder()
                                .roleId(roleId)
                                .userId(userRoleVO.getUserId())
                                .build();
        roleUserMapper.update(roleUser, new LambdaQueryWrapper<RoleUser>()
                .eq(RoleUser::getUserId, userRoleVO.getUserId()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendCode(String email) {
        Integer isEmailNotice = websiteConfigService.getWebsiteConfig().getIsEmailNotice();
        if (isEmailNotice == 0) {
            throw new ServiceException(FAIL.getCode(), "管理员已关闭邮箱通知");
        }
        if (!checkEmail(email)) {
            throw new ServiceException(FAIL.getCode(),"邮箱格式不正确");
        }
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(email)
                .subject(EMAIL_SUBJECT + "验证码")
                .content(EMAIL_TEXT_PRE + code + EMAIL_TEXT_POST)
                .build();
        // 发送到消息队列中
        rabbitTemplate.convertAndSend(FANOUT_EMAIL_EXCHANGE,"*",new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        //在把验证码放到redis 有效时间存为15分钟
        redisService.set(USER_EMAIL_KEY + email, code, CODE_EXPIRE_TIME);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerUser(UserRegisterVO userRegisterVO) {
        WebsiteConfigVO websiteConfig = websiteConfigService.getWebsiteConfig();
        String email = confirmEmailCode(userRegisterVO, redisService);
        User user = User.builder()
                .avatar(websiteConfig.getUserAvatar())
                .password(BCrypt.hashpw(userRegisterVO.getPassword(), BCrypt.gensalt()))
                .email(email)
                .username(email)
                .loginType(EMAIL.getId())
                .nickname(NICKNAME + UUID.randomUUID().toString().
                                replaceAll("-","").
                                replaceAll("[a-zA-Z]", String.valueOf(new Random().nextInt(9) + 1)).
                                substring(0,9))
                .build();
        if (Objects.nonNull(userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email)))) {
            throw new ServiceException(FAIL.getCode(),"邮箱已被他人占用");
        } else {
            userMapper.insert(user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void forgetPassword(UserRegisterVO userRegisterVO) {
        String email = confirmEmailCode(userRegisterVO, redisService);
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        if (existUser != null) {
            User user = User
                    .builder()
                    .password(BCrypt.hashpw(userRegisterVO.getPassword(), BCrypt.gensalt()))
                    .build();
            userMapper.update(user,new LambdaQueryWrapper<User>().eq(User::getEmail, email));
        } else {
            throw new ServiceException(FAIL.getCode(),"用户不存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User qqLogin(QQLoginVO qqLoginVO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return socialLoginStrategyContext.executeLoginStrategy(mapper.writeValueAsString(qqLoginVO), QQ.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveEmail(UserRegisterVO userRegisterVO) {
        String email = confirmEmailCode(userRegisterVO, redisService);
        User loginUser = UserUtil.getLoginUser();
        assert loginUser != null;
        if (loginUser.getEmail() != null && loginUser.getEmail().equals(email)) {
            throw new ServiceException(FAIL.getCode(), "该邮箱已被绑定");
        } else  {
            loginUser.setEmail(email);
            try {
                userMapper.update(loginUser, new LambdaQueryWrapper<User>().eq(User::getUserId,loginUser.getUserId()));
            } catch (Exception e) {
                log.info("堆栈信息：{}",e.getMessage());
                throw new ServiceException(FAIL.getCode(), "该邮箱已被绑定");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        User loginUser = UserUtil.getLoginUser();
        assert loginUser != null;
        loginUser.
                setWebSite(userInfoVO.getWebSite()).
                setNickname(userInfoVO.getNickname()).
                setIntro(userInfoVO.getIntro());
        try {
            userMapper.update(loginUser, new LambdaQueryWrapper<User>().eq(User::getUserId, userInfoVO.getUserId()));
        } catch (Exception e) {
            throw new ServiceException(FAIL.getCode(), FAIL.getDesc());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(PasswordVO passwordVO) {
        User loginUser = Objects.requireNonNull(UserUtil.getLoginUser());
        ConfirmPasswordBackInfo(passwordVO, loginUser);
        loginUser.setPassword(BCrypt.hashpw(passwordVO.getNewPassword(), BCrypt.gensalt()));
        userMapper.update(loginUser, new LambdaQueryWrapper<User>().eq(User::getUserId, loginUser.getUserId()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String uploadAvatar(MultipartFile file) {
        String src = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        User user =User
                        .builder()
                        .userId(Objects.requireNonNull(UserUtil.getLoginUser()).getUserId())
                        .avatar(src)
                        .build();
        userMapper.updateById(user);
        return src;
    }
}
