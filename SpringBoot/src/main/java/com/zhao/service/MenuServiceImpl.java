package com.zhao.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.MenuService;
import com.zhao.dto.MenuDTO;
import com.zhao.mapper.MenuMapper;
import com.zhao.pojo.Menu;
import com.zhao.utils.BeanCopyUtil;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.MenuHiddenVO;
import com.zhao.vo.MenuSaveVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.zhao.constant.CommonConst.LAYOUT;

@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getMenuListByRoleName(String roleName) {
        // 获取当前角色所有的菜单
        List<Menu> menus = menuMapper.getMenuListByRoleName(roleName);
        // 添加用户角色对应的菜单及其递归查询子菜单
        return recursiveQueryMenu(menus);
    }

    @Override
    public List<MenuDTO> getAllMenuList(ConditionVO conditionVO) {
        // 获取所有的菜单
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>()
                .like(conditionVO.getKeywords() != null ,
                        Menu::getName,
                        conditionVO.getKeywords())
                .orderByAsc(Menu::getOrderNum));
        // 添加用户角色对应的菜单及其递归查询子菜单
        return recursiveQueryAllMenu(menus);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateHiddenById(MenuHiddenVO hiddenVO) {
        menuMapper.updateHiddenById(hiddenVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenuById(Integer id) {
        menuMapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateMenu(MenuSaveVO menuSaveVO) {
        saveOrUpdate(BeanCopyUtil.copyObject(menuSaveVO, Menu.class));
    }

    private List<MenuDTO> recursiveQueryMenu(List<Menu> menus) {
        List<MenuDTO> menuDTOList = new ArrayList<>();
        int[] used = new int[menus.size()];
        Arrays.fill(used, 0);
        for (int i = 0; i < menus.size(); i++) {
            // 证明是顶级父类
            if (menus.get(i).getParentId() == null || menus.get(i).getParentId() == 0) {
                MenuDTO menuDTO = new MenuDTO();
                BeanUtil.copyProperties(menus.get(i),menuDTO,true);
                used[i] = 1;
                findChildren(menuDTO, menus, used);
                if (!menuDTO.getComponent().equals(LAYOUT)) {
                    MenuDTO menuDTO1 = new MenuDTO();
                    menuDTO1.setComponent(LAYOUT);
                    menuDTO1.setPath("");
                    menuDTO1.getChildren().add(menuDTO);
                    menuDTOList.add(menuDTO1);
                } else {
                    menuDTOList.add(menuDTO);
                }
            }
        }
        return menuDTOList;
    }

    private List<MenuDTO> recursiveQueryAllMenu(List<Menu> menus) {
        List<MenuDTO> menuDTOList = new ArrayList<>();
        int[] used = new int[menus.size()];
        Arrays.fill(used, 0);
        for (int i = 0; i < menus.size(); i++) {
            // 证明是顶级父类
            if (menus.get(i).getParentId() == null || menus.get(i).getParentId() == 0) {
                MenuDTO menuDTO = new MenuDTO();
                BeanUtil.copyProperties(menus.get(i),menuDTO,true);
                used[i] = 1;
                findChildren(menuDTO, menus, used);
                menuDTOList.add(menuDTO);
            }
        }
        return menuDTOList;
    }

    private void findChildren(MenuDTO menu, List<Menu> menus, int[] used) {
        for (int i = 0 ;i < menus.size(); i++) {
            if (used[i] == 1) {
                continue;
            }
            if (Objects.equals(menus.get(i).getParentId(), menu.getId())) {
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
