package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.FriendLinkService;
import com.zhao.dto.FriendLinkBackDTO;
import com.zhao.dto.PageDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zhao.constant.OptTypeConst.*;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;


/**
 * 友链控制器
 *
 * @author ran-feiran
 * @date 2022/10/14
 */
@RestController
@RequestMapping("/friendLink")
@Api(tags = "友链模块")
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;


    @ApiOperation(value = "获取友链列表")
    @GetMapping("/listLinks")
    public ResultStandby<PageDTO<FriendLinkBackDTO>> listLinks(ConditionVO conditionVO) {
        return success(friendLinkService.getFriendLinkList(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "新增或更新友链")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/addOrEditFriendLink")
    public ResultStandby<?> addOrEditFriendLink(@RequestBody FriendLinkBackDTO friendLink) {
        friendLinkService.addOrEditFriendLink(friendLink);
        return success();
    }

    @ApiOperation(value = "删除友链")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/del/batch")
    public ResultStandby<?> delBatchFriendLink(@RequestBody List<Integer> ids) {
        friendLinkService.removeBatchByIds(ids);
        return success();
    }
}
