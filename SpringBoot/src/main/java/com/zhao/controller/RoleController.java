package com.zhao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.MenuService;
import com.zhao.api.RoleService;
import com.zhao.dto.RoleListDto;
import com.zhao.exception.div.ServiceException;
import com.zhao.pojo.Menu;
import com.zhao.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

//    @Autowired
//    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuService menuService;

    @PostMapping("/saveRoleMenu/{roleId}")
    public Result saveRoleMenu(@PathVariable("roleId") Integer roleId, @RequestBody List<Integer> menuIds) {
        try {
            if (menuIds.size() > 0) {
                List<Integer> menus = new ArrayList<>();
                menus.addAll(menuIds);

                for (Integer menuId : menuIds) {
                    QueryWrapper<Menu> wrapper = new QueryWrapper<>();
                    wrapper.eq("menu_id", menuId);
                    Menu menu = menuService.getOne(wrapper);
                    if (menu.getParentId() != 0 && menu.getParentId() != null && !menuIds.contains(menu.getParentId())) {
                        menus.add(menu.getParentId());
                    }
                }

                roleService.saveRoleMenu(roleId, menus);
            } else {
                return Result.error(ResultInfo.CODE_600, "菜单赋权为空,请重新赋权");
            }
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"修改菜单权限失败");
        }
        return Result.success();
    }

    @GetMapping("/getRoleList")
    public Result getRoleList(){
        List<RoleListDto> roleList = roleService.queryRole();
        if (roleList == null) {
            throw new ServiceException(ResultInfo.CODE_600,"没有角色存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("roleList", roleList);
        return Result.success(map,"获取角色成功");
    }

    @GetMapping("/findRoleList")
    public Result findRoleList() {
        List<Role> list = null;
        try {
            list = roleService.list();
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"没有角色存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("roleList", list);
        return Result.success(map,"获取角色成功");
    }

    @PostMapping("/saveOrUpdateRole")
    public Result saveOrUpdateRole(@RequestBody Role role) {
        boolean b = roleService.saveOrUpdate(role);
        if (!b) {
            throw new ServiceException(ResultInfo.CODE_600,"操作失败，请重试");
        }
        return Result.success(null, "操作成功");
    }

    @DeleteMapping("/deleteRole")
    public Result deleteRole(@RequestParam("id") Integer id) {
        try {
            roleService.removeById(id);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"操作失败，请重试");
        }
        return Result.success(null,"删除成功");
    }

    @GetMapping("/getRoleNameById")
    public Result getRoleNameById(@RequestParam("roleId") Integer roleId) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        Role role = roleService.getOne(wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("roleName", role.getRoleName());
        return Result.success(map, "");
    }
}
