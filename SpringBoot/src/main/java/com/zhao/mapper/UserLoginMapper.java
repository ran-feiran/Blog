package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.UserLoginDTO;
import com.zhao.pojo.UserLogin;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserLoginMapper extends BaseMapper<UserLogin> {

    /**
     * 得到用户信息列表
     *
     * @param conditionVO 条件签证官
     * @return {@link List}<{@link UserLoginDTO}>
     */
    List<UserLoginDTO> getUserInfoList(@Param("condition") ConditionVO conditionVO);
}
