package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhao.dto.PageDTO;
import com.zhao.dto.UserDTO;
import com.zhao.dto.UserSignalDTO;
import com.zhao.pojo.User;
import com.zhao.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

/**
 * 用户服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface UserService extends IService<User> {

    /**
     * 通过用户id获取用户角色
     *
     * @param id id
     * @return {@link List}<{@link String}>
     */
    List<String> getUserRolesByUserId(Integer id);

    /**
     * 获取用户列表
     *
     * @param conditionVO 条件签证官
     * @return {@link PageDTO}<{@link UserDTO}>
     */
    PageDTO<UserDTO> getUserList(ConditionVO conditionVO);


    /**
     *
     * 通过用户id更新用户禁言情况
     *
     * @param userSilenceVO 用户禁言签证官
     */
    void updateSilenceById(UserSilenceVO userSilenceVO);

    /**
     * 得到用户角色列表
     *
     * @param current  当前
     * @param size     大小
     * @param nickname 昵称
     * @return {@link List}<{@link UserSignalDTO}>
     */
    List<UserSignalDTO> getUserRoleList(Integer current, Integer size, String nickname);

    /**
     * 更新用户角色
     *
     * @param userRoleVO 用户角色签证官
     */
    void updateUserRole(UserRoleVO userRoleVO);

    /**
     * 发送邮件
     *
     * @param email 电子邮件
     * @throws MessagingException 通讯异常
     */
    void sendCode(String email) throws MessagingException;

    /**
     * 注册用户
     *
     * @param userRegisterVO 用户注册签证官
     */
    void registerUser(UserRegisterVO userRegisterVO);

    /**
     * 忘记密码
     *
     * @param userRegisterVO 用户注册签证官
     */
    void forgetPassword(UserRegisterVO userRegisterVO);

    /**
     * qq登录
     *
     * @param qqLoginVO qq登录签证官
     * @return {@link User}
     * @throws JsonProcessingException json处理异常
     */
    User qqLogin(QQLoginVO qqLoginVO) throws JsonProcessingException;

    /**
     * 保存邮箱
     *
     * @param userRegisterVO 用户注册签证官
     */
    void saveEmail(UserRegisterVO userRegisterVO);

    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息签证官
     */
    void updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 更新密码
     *
     * @param passwordVO 密码签证官
     */
    void updatePassword(PasswordVO passwordVO);

    /**
     * 上传头像
     *
     * @param file 文件
     * @return {@link String}
     */
    String uploadAvatar(MultipartFile file);
}
