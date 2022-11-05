package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.OperationLogDTO;
import com.zhao.pojo.OperationLog;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    List<OperationLogDTO> listLogs(@Param("condition") ConditionVO conditionVO);
}
