package com.zhao.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.ApiBackDTO;
import com.zhao.dto.ApiPermissionDTO;
import com.zhao.pojo.Api;

import java.util.List;

public interface ApiService extends IService<Api> {

    //分页查出所有满足条件的
    List<ApiBackDTO> listApiInfoBack(Integer apiId);

    void saveRolePermissionList(Integer roleId, List<Integer> apiList);

    List<ApiPermissionDTO> getAllApiPermission();
}
