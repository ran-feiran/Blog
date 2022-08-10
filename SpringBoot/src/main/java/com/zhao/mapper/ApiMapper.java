package com.zhao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.ApiBackDTO;
import com.zhao.dto.ApiPermissionDTO;
import com.zhao.pojo.Api;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Stack;

@Mapper
@Repository
public interface ApiMapper extends BaseMapper<Api> {

    //分页查出所有满足条件的
    List<ApiBackDTO> listApiInfoBack(@Param("apiId") Integer apiId);

    List<ApiPermissionDTO> getAllApiPermission();

    List<String> getRoleListByApiId(@Param("apiId") Integer apiId);
}
