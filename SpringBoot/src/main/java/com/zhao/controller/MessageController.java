package com.zhao.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.api.MessageService;
import com.zhao.exception.div.ServiceException;
import com.zhao.pojo.Message;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/getMessageList")
    public Result listMessages(@RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
                               @RequestParam(value = "size",required = false,defaultValue = "9999") Integer size,
                               @RequestParam(value = "nickname",required = false) String nickname) {
        IPage<Message> page = messageService.getMessageList(current, size, nickname);
        long count = page.getTotal();
        List<Message> data = page.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("messageList", data);
        map.put("count", count);
        return Result.success(map, "获取留言成功");
    }

    @PostMapping("/messages")
    public Result saveMessages(@RequestBody MessageVO messageVO) {
       messageService.saveMessage(messageVO);
       return Result.success(null, "留言成功");
    }

    @PostMapping("/deleteMessages")
    public Result deleteMessages(@RequestBody List<Integer> ids) {
        try {
            messageService.removeByIds(ids);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"删除失败");
        }
        return Result.success(null,"删除成功");
    }
}
