package com.zhao.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.FriendLinkService;
import com.zhao.exception.div.ServiceException;
import com.zhao.pojo.FriendLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friendLink")
public class FriendLinkController {

    @Autowired
    FriendLinkService friendLinkService;


    @GetMapping("/listLinks")
    public Result listLinks(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                            @RequestParam(value = "size", required = false, defaultValue = "9999") Integer size,
                            @RequestParam(value = "nickname", required = false) String nickname) {
        IPage<FriendLink> page = friendLinkService.getFriendLinkList(current, size, nickname);
        long count = page.getTotal();
        List<FriendLink> data = page.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("linkList", data);
        map.put("count", count);
        return Result.success(map, "获取友链成功");
    }

    @PostMapping("/addOrEditFriendLink")
    public Result addOrEditFriendLink(@RequestBody FriendLink friendLink) {
        try {
            friendLinkService.saveOrUpdate(friendLink);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600, "友链异常,重新提交");
        }
        return Result.success();
    }
}
