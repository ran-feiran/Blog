package com.zhao.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.MenuService;
import com.zhao.dto.MenuDTO;
import com.zhao.mapper.MenuMapper;
import com.zhao.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getMenuListByRoleName(String roleName) {
        // 获取当前角色所有的菜单
        List<Menu> menus = menuMapper.getMenuListByRoleName(roleName);
//        System.out.println(menus);
        List<MenuDTO> menuDTOList = new ArrayList<>();
        int[] used = new int[menus.size()];
        Arrays.fill(used, 0);
        for (int i = 0; i < menus.size(); i++) {
            // 证明是顶级父类
            if (menus.get(i).getParentId() == 0 || menus.get(i).getParentId() == null) {
                MenuDTO menuDTO = new MenuDTO();
                BeanUtil.copyProperties(menus.get(i),menuDTO,true);
                used[i] = 1;
                findChildren(menuDTO,menus,used);
                menuDTOList.add(menuDTO);
            }
        }


//        for (int i = 0; i < used.length; i++) {
//            if (used[i] == 0) {
//                MenuDTO menuDTO = new MenuDTO();
//                BeanUtil.copyProperties(menus.get(i),menuDTO,true);
//                used[i] = 1;
//                menuDTOList.add(menuDTO);
//            }
//        }

        return menuDTOList;
    }

    @Override
    public List<MenuDTO> getAllMenuList() {
        // 获取所有的菜单
        List<Menu> menus = menuMapper.selectList(null);
//        System.out.println(menus);
        List<MenuDTO> menuDTOList = new ArrayList<>();
        int[] used = new int[menus.size()];
        Arrays.fill(used, 0);
        for (int i = 0; i < menus.size(); i++) {
            // 证明是顶级父类
            if (menus.get(i).getParentId() == 0 || menus.get(i).getParentId() == null) {
                MenuDTO menuDTO = new MenuDTO();
                BeanUtil.copyProperties(menus.get(i),menuDTO,true);
                used[i] = 1;
                findChildren(menuDTO,menus,used);
                menuDTOList.add(menuDTO);
            }
        }
        return menuDTOList;
    }


    public void findChildren(MenuDTO menu,List<Menu> menus,int[] used) {
        for (int i = 0 ;i < menus.size(); i++) {
            if (used[i] == 1) {
                continue;
            }
            if (Objects.equals(menus.get(i).getParentId(), menu.getMenuId())) {
                used[i] = 1;
                MenuDTO menuDTOChildren = new MenuDTO();
                BeanUtil.copyProperties(menus.get(i),menuDTOChildren,true);
                menu.getChildren().add(menuDTOChildren);
            }
        }
        // 遍历完，没有子菜单就说明找完了
        if (menu.getChildren().size() == 0) {
            return;
        }
        // 遍历子菜单的子菜单
        for (MenuDTO child : menu.getChildren()) {
            findChildren(child, menus, used);
        }

    }
}
