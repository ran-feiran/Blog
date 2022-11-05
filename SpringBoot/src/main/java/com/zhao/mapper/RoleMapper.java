package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.RoleBackDTO;
import com.zhao.dto.RoleListDTO;
import com.zhao.pojo.Role;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色
     *
     * @return {@link List}<{@link RoleListDTO}>
     */
    List<RoleListDTO> queryRole();

    /**
     * 角色列表
     *
     * @param conditionVO 条件签证官
     * @return {@link List}<{@link RoleBackDTO}>
     */
    List<RoleBackDTO> listRoles(@Param("condition") ConditionVO conditionVO);
}
