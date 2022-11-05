package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.PageDTO;
import com.zhao.dto.RoleBackDTO;
import com.zhao.dto.RoleListDTO;
import com.zhao.pojo.Role;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.RolePermissionVO;

import java.util.List;

/**
 * 角色服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface RoleService extends IService<Role> {

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
     * @return {@link PageDTO}<{@link RoleBackDTO}>
     */
    PageDTO<RoleBackDTO> listRoles(ConditionVO conditionVO);

    /**
     * 保存或更新角色权限
     *
     * @param rolePermissionVO 角色许可签证官
     */
    void saveOrUpdateRolePermission(RolePermissionVO rolePermissionVO);


}
