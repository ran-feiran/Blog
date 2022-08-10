package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.RoleListDto;
import com.zhao.pojo.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<RoleListDto> queryRole();


    void saveRoleMenu(Integer roleId, List<Integer> menuIds);
    
}
