package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.UserLoginService;
import com.zhao.dto.PageDTO;
import com.zhao.dto.UserLoginDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.zhao.constant.OptTypeConst.REMOVE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

/**
 * 用户登录控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@Api(tags = "用户登录信息模块")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @ApiOperation(value = "获取在线用户列表")
    @GetMapping("/userLogin/getOnlineUser")
    public ResultStandby<PageDTO<UserLoginDTO>> getUserInfoList(ConditionVO conditionVO) {
        return success(userLoginService.getUserInfoList(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "下线用户")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/userLogin/del/online/{userLoginId}")
    public ResultStandby<?> deleteUser(@PathVariable("userLoginId") Integer userLoginId) {
        userLoginService.removeById(userLoginId);
        return success(null, SUCCESS.getDesc());
    }
}
