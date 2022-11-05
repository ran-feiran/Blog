package com.zhao.controller;


import com.zhao.annotations.OptLog;
import com.zhao.api.RoleService;
import com.zhao.dto.PageDTO;
import com.zhao.dto.RoleBackDTO;
import com.zhao.dto.RoleListDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.RolePermissionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zhao.constant.OptTypeConst.SAVE_OR_UPDATE;
import static com.zhao.constant.OptTypeConst.UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;


/**
 * 角色控制器
 *
 * @author ran-feiran
 * @date 2022/10/18
 */
@RestController
@Api(tags = "角色模块")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色数据")
    @GetMapping("/role/getRoleList")
    public ResultStandby<List<RoleListDTO>> getRoleList() {
        return success(roleService.queryRole(), SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取角色列表")
    @GetMapping("/role/listRoles")
    public ResultStandby<PageDTO<RoleBackDTO>> listRoles(ConditionVO conditionVO){
        return success(roleService.listRoles(conditionVO), SUCCESS.getDesc());
    }

    @ApiOperation(value = "角色删除")
    @OptLog(optType = UPDATE)
    @DeleteMapping("/role/del/batch")
    public ResultStandby<?> deleteRole(@RequestBody List<Integer> ids) {
        roleService.removeByIds(ids);
        return success();
    }

    @ApiOperation(value = "保存或更新角色权限")
    @OptLog(optType = SAVE_OR_UPDATE)
    @PostMapping("/role/saveOrUpdateRolePermission")
    public ResultStandby<?> saveOrUpdateRolePermission(@RequestBody RolePermissionVO rolePermissionVO) {
        roleService.saveOrUpdateRolePermission(rolePermissionVO);
        return success();
    }
}
