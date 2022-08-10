package com.zhao.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.MenuDTO;
import com.zhao.pojo.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<MenuDTO> getMenuListByRoleName(String roleName);

    List<MenuDTO> getAllMenuList();
}
