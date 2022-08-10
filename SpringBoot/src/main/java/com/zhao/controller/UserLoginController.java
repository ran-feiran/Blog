package com.zhao.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.result.Result;
import com.zhao.api.UserLoginService;
import com.zhao.pojo.UserLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户登录信息模块")
@RequestMapping("/userLogin")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @ApiOperation(value = "分页获取用户登录信息列表")
    @GetMapping("/getUserInfoList")
    public Result getUserInfoList(@RequestParam("pageNum") Integer pageNum,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam("nickname") String nickname) {
        IPage<UserLogin> page = userLoginService.getUserInfoList(pageNum, pageSize, nickname);
        long total = page.getTotal();
        List<UserLogin> data = page.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("userLoginInfoList",data);
        map.put("total",total);
        return Result.success(map,"获取登录数据成功");
    }

    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestParam("id") Integer userLoginId) {
        userLoginService.removeById(userLoginId);
        return Result.success();
    }
}
