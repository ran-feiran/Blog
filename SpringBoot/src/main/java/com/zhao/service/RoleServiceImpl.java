package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.RoleApiService;
import com.zhao.api.RoleMenuService;
import com.zhao.api.RoleService;
import com.zhao.authority.MyFilterInvocationSecurityMetadataSource;
import com.zhao.dto.PageDTO;
import com.zhao.dto.RoleBackDTO;
import com.zhao.dto.RoleListDTO;
import com.zhao.exception.ServiceException;
import com.zhao.mapper.RoleMapper;
import com.zhao.pojo.Role;
import com.zhao.pojo.RoleApi;
import com.zhao.pojo.RoleMenu;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.RolePermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zhao.constant.CommonConst.FALSE;
import static com.zhao.enums.StatusCodeEnum.SYSTEM_ERROR;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleApiService roleApiService;
    @Autowired
    private MyFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Override
    public List<RoleListDTO> queryRole() {
        return roleMapper.queryRole();
    }

    @Override
    public PageDTO<RoleBackDTO> listRoles(ConditionVO conditionVO) {
        Long count = roleMapper.selectCount(null);
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        return new PageDTO<>(roleMapper.listRoles(conditionVO), count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRolePermission(RolePermissionVO rolePermissionVO) {
        // 判断角色名重复
        Role existRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleName, rolePermissionVO.getRoleName()));
        if (Objects.nonNull(existRole) && !existRole.getRoleId().equals(rolePermissionVO.getRoleId())) {
            throw new ServiceException(SYSTEM_ERROR.getCode(), "角色名已存在");
        }
        // 保存或更新角色信息
        Role role = Role.builder()
                .roleId(rolePermissionVO.getRoleId())
                .roleName(rolePermissionVO.getRoleName())
                .roleLabel(rolePermissionVO.getRoleLabel())
                .isDisable(FALSE)
                .build();
        saveOrUpdate(role);
        // 更新角色资源关系
        if (Objects.nonNull(rolePermissionVO.getResourceIdList())) {
            if (Objects.nonNull(rolePermissionVO.getRoleId())) {
                roleApiService.remove(new LambdaQueryWrapper<RoleApi>()
                        .eq(RoleApi::getRoleId, rolePermissionVO.getRoleId()));
            }
            // 创建资源列表
            List<RoleApi> roleResourceList = rolePermissionVO.getResourceIdList().stream()
                    .map(item -> RoleApi.builder()
                            .roleId(role.getRoleId())
                            .resourceId(item)
                            .build())
                    .collect(Collectors.toList());
            // 保存所有的接口资源
            roleApiService.saveBatch(roleResourceList);
            // 重新加载角色资源信息
            filterInvocationSecurityMetadataSource.clearDataSource();
        }
        // 更新角色菜单关系
        if (Objects.nonNull(rolePermissionVO.getMenuIdList())) {
            if (Objects.nonNull(rolePermissionVO.getRoleId())) {
                roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getRoleId, rolePermissionVO.getRoleId()));
            }
            // 创建菜单列表
            List<RoleMenu> roleMenuList = rolePermissionVO.getMenuIdList().stream()
                    .map(item -> RoleMenu.builder()
                            .roleId(role.getRoleId())
                            .menuId(item)
                            .build())
                    .collect(Collectors.toList());
            // 保存所有的菜单资源
            roleMenuService.saveBatch(roleMenuList);
        }
    }


}
