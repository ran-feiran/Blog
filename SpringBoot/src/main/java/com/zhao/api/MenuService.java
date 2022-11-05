package com.zhao.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.MenuDTO;
import com.zhao.pojo.Menu;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.MenuHiddenVO;
import com.zhao.vo.MenuSaveVO;

import java.util.List;

/**
 * 菜单服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface MenuService extends IService<Menu> {

    /**
     * 通过角色获取菜单列表
     *
     * @param roleName 角色名
     * @return {@link List}<{@link MenuDTO}>
     */
    List<MenuDTO> getMenuListByRoleName(String roleName);

    /**
     * 得到所有菜单列表
     *
     * @return {@link List}<{@link MenuDTO}>
     * @param conditionVO
     */
    List<MenuDTO> getAllMenuList(ConditionVO conditionVO);

    /**
     * 更新隐藏状态
     *
     * @param hiddenVO 隐藏签证官
     */
    void updateHiddenById(MenuHiddenVO hiddenVO);

    /**
     * 删除菜单通过id
     *
     * @param id id
     */
    void deleteMenuById(Integer id);

    /**
     * 保存或更新菜单
     *
     * @param menuSaveVO 菜单保存签证官
     */
    void saveOrUpdateMenu(MenuSaveVO menuSaveVO);
}
