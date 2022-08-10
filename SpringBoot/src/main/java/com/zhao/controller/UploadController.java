package com.zhao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.api.UserService;
import com.zhao.pojo.User;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.UploadService;
import com.zhao.exception.div.ServiceException;
import com.zhao.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UserService userService;

    @PostMapping("/uploadImage")
    public Result uploadImage(MultipartFile file) {
        String url = null;
        try {
            url = uploadService.upload(file);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600, "上传失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("url",url);
        return Result.success(map, "上传成功");
    }

    @GetMapping("/download/{fileUUID}")
    public Result downLoadImage(@PathVariable("fileUUID") String fileUUID,
                                HttpServletResponse response) throws IOException {
        uploadService.downLoad(fileUUID, response);
        return Result.success();
    }

    @PostMapping("/user/avatar")
    public Result uploadAvatar(MultipartFile file) throws IOException {
        String url = null;
        try {
            url = uploadService.upload(file);
            User loginUser = UserUtil.getLoginUser(); // 获得当前登录的用户所以信息
            assert loginUser != null;
            loginUser.setAvatar(url);
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",loginUser.getUserId());
            userService.update(loginUser,wrapper);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600, "上传失败~~");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("url",url);
        return Result.success(map, "上传成功");
    }

}
