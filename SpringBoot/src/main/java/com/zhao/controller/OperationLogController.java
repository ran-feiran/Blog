package com.zhao.controller;

import com.zhao.annotations.OptLog;
import com.zhao.api.OperationLogService;
import com.zhao.dto.OperationLogDTO;
import com.zhao.dto.PageDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zhao.constant.OptTypeConst.REMOVE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

@RestController
@Api(tags = "日志模块")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "获取操作日志")
    @GetMapping("/operation/listLogs")
    public ResultStandby<PageDTO<OperationLogDTO>> listLogs(ConditionVO conditionVO) {
        return success(operationLogService.listLogs(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "删除日志")
    @OptLog(optType = REMOVE)
    @DeleteMapping("/operation/del/batch")
    public ResultStandby<?> deleteOpt(@RequestBody List<Integer> ids) {
        operationLogService.removeBatchByIds(ids);
        return success();
    }
}
