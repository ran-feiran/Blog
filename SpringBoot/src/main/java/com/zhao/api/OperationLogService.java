package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.OperationLogDTO;
import com.zhao.dto.PageDTO;
import com.zhao.pojo.OperationLog;
import com.zhao.vo.ConditionVO;

public interface OperationLogService extends IService<OperationLog> {

    PageDTO<OperationLogDTO> listLogs(ConditionVO conditionVO);
}
