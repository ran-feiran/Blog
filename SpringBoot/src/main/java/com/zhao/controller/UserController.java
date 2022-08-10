package com.zhao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.pojo.User;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.UserService;
import com.zhao.dto.UserDTO;
import com.zhao.dto.UserSignalDTO;
import com.zhao.exception.div.ServiceException;
import com.zhao.vo.UserInfoVO;
import com.zhao.vo.UserQueryVO;
import com.zhao.vo.UserRegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/forgetPassword")
    public Result forgetPassword(@RequestBody UserRegisterVO userRegisterVO) {
        userService.forgetPassword(userRegisterVO);
        return Result.success(null,"修改成功，请登录");
    }

    @PostMapping("/registerUser")
    public Result registerUser(@RequestBody UserRegisterVO userRegisterVO) {
        userService.registerUser(userRegisterVO);
        return Result.success(null, "注册成功，请登录");
    }

    @GetMapping("/sendEmailCode")
    public Result sendEmailCode(@RequestParam("email") String email) {
        userService.sendCode(email);
        return Result.success(null,"验证码发送成功,请注意查收");
    }


    @GetMapping("/getUserList")
    public Result getUserList(UserQueryVO userQueryVO) {
        userQueryVO.setStartIndex((userQueryVO.getStartIndex()-1) * userQueryVO.getPageSize());
        System.out.println(userQueryVO.getStartIndex());
        List<UserDTO> userList = userService.getUserList(userQueryVO);
        long total = userService.count();
        if (userList == null) {
            throw new ServiceException(ResultInfo.CODE_600,"没有用户存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("total", total);
        return Result.success(map, "加载用户列表完成");
    }


    @GetMapping("/getUserListSignal")
    public Result getUserListSignal(@RequestParam("current") Integer current,
                                    @RequestParam("size") Integer size,
                                    @RequestParam("nickname") String nickname) {
        current = (current - 1) * size;
        List<UserSignalDTO> userList = userService.getUserRoleList(current, size, nickname);
        long total = userService.count();
        if (userList == null) {
            throw new ServiceException(ResultInfo.CODE_600,"没有用户存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("total", total);
        return Result.success(map, "成功");
    }

    @PutMapping("/updateUserById")
    public Result updateUserById(@RequestBody UserDTO userDTO) {
        int i = userService.updateUserById(userDTO);
        if (i <= 0){
            throw new ServiceException(ResultInfo.CODE_600,"修改用户失败");
        }
        return Result.success(null,"修改用户成功");
    }

    @PostMapping("/updateUserRole")
    public Result updateUserRole(@RequestBody UserSignalDTO userSignalDTO) {
        int i = userService.updateUserRole(userSignalDTO);
        if (i <= 0) {
            throw new ServiceException(ResultInfo.CODE_600,"修改角色失败");
        }
        return Result.success(null,"修改成功");
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody UserDTO userDTO) {
        String password = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        userDTO.setPassword(password);
        int i = userService.addUser(userDTO);
        if (i <= 0){
            throw new ServiceException(ResultInfo.CODE_600,"添加用户失败");
        }
        return Result.success(null,"添加用户成功");
    }

    @PutMapping("/updateSilenceById")
    public Result updateSilenceById(@RequestParam("flag") boolean isSilence,
                                    @RequestParam("id") Integer userId) {
        int i = userService.updateSilenceById(isSilence, userId);
        if (i <= 0){
            throw new ServiceException(ResultInfo.CODE_600,"状态出错");
        }
        return Result.success();
    }

    @DeleteMapping("/logicDeleteUser")
    public Result logicDeleteUser(@RequestParam("id") Integer userId) {
        boolean b = userService.removeById(userId);
        if (!b) {
            throw new ServiceException(ResultInfo.CODE_600,"状态出错");
        }
        return Result.success(null,"删除用户成功");
    }

    @PutMapping("/info")
    public Result updateUserInfo(@RequestBody UserInfoVO userInfoVO) {
        User user = new User();
        user.
                setWebSite(userInfoVO.getWebSite()).
                setNickname(userInfoVO.getNickname()).
                setIntro(userInfoVO.getIntro()).
                setEmail(userInfoVO.getEmail());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userInfoVO.getUserId());
        try {
            userService.update(user,wrapper);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"修改失败");
        }
        return Result.success();
    }
}
