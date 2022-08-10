package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuListByRoleName(@Param("roleName") String roleName);
}
