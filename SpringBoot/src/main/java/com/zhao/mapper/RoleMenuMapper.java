package com.zhao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.pojo.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
}
