package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.result.ResultInfo;
import com.zhao.api.RoleService;
import com.zhao.dto.RoleListDto;
import com.zhao.exception.div.ServiceException;
import com.zhao.mapper.RoleMapper;
import com.zhao.mapper.RoleMenuMapper;
import com.zhao.pojo.Role;
import com.zhao.pojo.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleListDto> queryRole() {
        return roleMapper.queryRole();
    }

    @Override
    public void saveRoleMenu(Integer roleId, List<Integer> menuIds) {
        for (Integer menuId : menuIds) {
            QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",roleId);
            wrapper.eq("menu_id",menuId);
            if (roleMenuMapper.selectOne(wrapper) == null) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menuId).setRoleId(roleId);
                int i = roleMenuMapper.insert(roleMenu);
                if (i <= 0) {
                    throw new ServiceException(ResultInfo.CODE_600,"修改菜单权限失败");
                }
            }
        }
        QueryWrapper<RoleMenu> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_id",roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper1);
        List<Integer> menus = roleMenus.
                stream().
                map(RoleMenu::getMenuId).
                collect(Collectors.toList());
        System.out.println(Arrays.toString(menus.toArray()));
        for (Integer id : menus) {
            if (!menuIds.contains(id)) {
                QueryWrapper<RoleMenu> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("role_id",roleId);
                wrapper2.eq("menu_id",id);
                int i = roleMenuMapper.delete(wrapper2);
                if (i <= 0) {
                    throw new ServiceException(ResultInfo.CODE_600,"修改菜单权限失败");
                }
            }
        }
    }
}
