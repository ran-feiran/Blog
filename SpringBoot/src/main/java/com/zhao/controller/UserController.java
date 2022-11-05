package com.zhao.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhao.annotations.AccessLimit;
import com.zhao.annotations.OptLog;
import com.zhao.api.UserService;
import com.zhao.dto.PageDTO;
import com.zhao.dto.UserDTO;
import com.zhao.pojo.User;
import com.zhao.result.ResultStandby;
import com.zhao.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;

import static com.zhao.constant.OptTypeConst.UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

/**
 * 用户控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "QQ登录")
    @PostMapping("/oauth/qq")
    public ResultStandby<User> oauthLoginQQ(@Valid @RequestBody QQLoginVO qqLoginVO) throws JsonProcessingException {
        return success(userService.qqLogin(qqLoginVO), "登录成功");
    }

    @ApiOperation(value = "绑定邮箱")
    @AccessLimit(seconds = 60 * 15, maxCount = 1, desc = "请求过于频繁，请稍候再试")
    @PostMapping("/saveEmail")
    public ResultStandby<?> saveEmail(@RequestBody UserRegisterVO userRegisterVO) {
        userService.saveEmail(userRegisterVO);
        return success(null, "绑定成功");
    }

    @ApiOperation(value = "更新密码")
    @AccessLimit(seconds = 60 * 15, maxCount = 1, desc = "请求过于频繁，请稍候再试")
    @PostMapping("/forgetPassword")
    public ResultStandby<?> forgetPassword(@RequestBody UserRegisterVO userRegisterVO) {
        userService.forgetPassword(userRegisterVO);
        return success(null,"修改成功，请登录");
    }

    @ApiOperation(value = "用户注册")
    @AccessLimit(seconds = 60 * 60, maxCount = 1, desc = "请求过于频繁，请稍候再试")
    @PostMapping("/registerUser")
    public ResultStandby<?> registerUser(@RequestBody UserRegisterVO userRegisterVO) {
        userService.registerUser(userRegisterVO);
        return success(null, "注册成功，请登录");
    }

    @ApiOperation(value = "发送验证码")
    @AccessLimit(seconds = 60, maxCount = 1, desc = "请求过于频繁，请稍候再试")
    @GetMapping("/sendEmailCode")
    public ResultStandby<?> sendEmailCode(@RequestParam("email") String email) throws MessagingException {
        userService.sendCode(email);
        return success(null,"验证码发送成功,请注意查收");
    }

    @ApiOperation(value = "更新用户角色")
    @PutMapping("/updateUserRole")
    public ResultStandby<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userService.updateUserRole(userRoleVO);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取用户列表(后台)")
    @GetMapping("/getUserList")
    public ResultStandby<PageDTO<UserDTO>> getUserList(ConditionVO conditionVO) {
        return success(userService.getUserList(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "更新用户禁言状态")
    @OptLog(optType = UPDATE)
    @PutMapping("/updateSilenceById")
    public ResultStandby<?> updateSilenceById(@Valid @RequestBody UserSilenceVO userSilenceVO) {
        userService.updateSilenceById(userSilenceVO);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "更新用户信息")
    @AccessLimit(seconds = 60, maxCount = 1, desc = "请求过于频繁，请稍候再试")
    @PutMapping("/info")
    public ResultStandby<?> updateUserInfo(@RequestBody UserInfoVO userInfoVO) {
        userService.updateUserInfo(userInfoVO);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "更新密码(后台)")
    @AccessLimit(seconds = 60 * 15, maxCount = 1, desc = "请求过于频繁，请稍候再试")
    @PutMapping("/password")
    public ResultStandby<?> updatePassword(@Valid @RequestBody PasswordVO passwordVO) {
        userService.updatePassword(passwordVO);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "上传头像")
    @AccessLimit(seconds = 60 * 15, maxCount = 1, desc = "请求过于频繁，请稍候再试")
    @PostMapping("/avatar")
    public ResultStandby<String> uploadAvatar(MultipartFile file) {
        return success(userService.uploadAvatar(file), SUCCESS.getDesc());
    }

}
