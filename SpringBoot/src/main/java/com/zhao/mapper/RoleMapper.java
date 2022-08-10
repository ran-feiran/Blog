package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.RoleListDto;
import com.zhao.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<RoleListDto> queryRole();
}
