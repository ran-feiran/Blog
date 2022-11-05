package com.zhao.controller;


import com.zhao.annotations.AccessLimit;
import com.zhao.annotations.OptLog;
import com.zhao.api.MessageService;
import com.zhao.dto.MessageDTO;
import com.zhao.dto.PageDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.MessageVO;
import com.zhao.vo.ReviewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.zhao.constant.OptTypeConst.REMOVE;
import static com.zhao.constant.OptTypeConst.UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

/**
 * 消息控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@RequestMapping("/message")
@Api(tags = "留言模块")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "获取留言列表(后台)")
    @GetMapping("/getMessageList")
    public ResultStandby<PageDTO<MessageDTO>> listMessages(ConditionVO conditionVO) {
        return success(messageService.getMessageList(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "留言审核")
    @OptLog(optType = UPDATE)
    @PutMapping("/review")
    public ResultStandby<?> updateMessageReview(@Valid @RequestBody ReviewVO reviewVO) {
        messageService.updateMessageReview(reviewVO);
        return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "新增留言")
    @AccessLimit(seconds = 60 * 15, maxCount = 1, desc = "请求过于频繁，请稍候再试") // 15分钟内留言一次
    @PostMapping("/messages")
    public ResultStandby<?> saveMessages(@RequestBody MessageVO messageVO) {
       messageService.saveMessage(messageVO);
       return success(null, SUCCESS.getDesc());
    }

    @ApiOperation(value = "留言删除")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/del/batch")
    public ResultStandby<?> deleteMessages(@RequestBody List<Integer> ids) {
        messageService.removeByIds(ids);
        return success(null,SUCCESS.getDesc());
    }
}
