package com.zhao.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.OperationLogService;
import com.zhao.dto.OperationLogDTO;
import com.zhao.dto.PageDTO;
import com.zhao.mapper.OperationLogMapper;
import com.zhao.pojo.OperationLog;
import com.zhao.vo.ConditionVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public PageDTO<OperationLogDTO> listLogs(ConditionVO conditionVO) {
        long count = count();
        if (count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        return new PageDTO<>(this.baseMapper.listLogs(conditionVO), count);
    }
}
