package com.zhao.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.dto.ApiPermissionDTO;
import com.zhao.result.ResultInfo;
import com.zhao.api.ApiService;
import com.zhao.dto.ApiBackDTO;
import com.zhao.exception.div.ServiceException;
import com.zhao.mapper.ApiMapper;
import com.zhao.mapper.RoleApiMapper;
import com.zhao.pojo.Api;
import com.zhao.pojo.RoleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

    @Autowired
    private RoleApiMapper roleApiMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public List<ApiBackDTO> listApiInfoBack(Integer apiId) {
        return this.baseMapper.listApiInfoBack(apiId);
    }

    @Override
    public void saveRolePermissionList(Integer roleId, List<Integer> apiList) {
        for (Integer apiId : apiList) {
            QueryWrapper<RoleApi> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",roleId);
            wrapper.eq("api_id",apiId);
            if (roleApiMapper.selectOne(wrapper) == null) {
                RoleApi roleApi = new RoleApi();
                roleApi.setApiId(apiId).setRoleId(roleId);
                int i = roleApiMapper.insert(roleApi);
                if (i <= 0) {
                    throw new ServiceException(ResultInfo.CODE_600,"修改API失败");
                }
            }
        }
        QueryWrapper<RoleApi> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        List<RoleApi> roleApis = roleApiMapper.selectList(wrapper);
        List<Integer> apis = roleApis.
                stream().
                map(RoleApi::getApiId).
                collect(Collectors.toList());
//        System.out.println(Arrays.toString(menus.toArray()));
        for (Integer id : apis) {
            if (!apiList.contains(id)) {
                QueryWrapper<RoleApi> wrapper2 = new QueryWrapper<>();
                wrapper2.eq("role_id",roleId);
                wrapper2.eq("api_id",id);
                int i = roleApiMapper.delete(wrapper2);
                if (i <= 0) {
                    throw new ServiceException(ResultInfo.CODE_600,"修改API失败");
                }
            }
        }
    }

    @Override
    public List<ApiPermissionDTO> getAllApiPermission() {
        List<ApiPermissionDTO> apiPermissionList = apiMapper.getAllApiPermission();
        for (ApiPermissionDTO apiPermissionDTO : apiPermissionList) {
            List<String> roleList = apiMapper.getRoleListByApiId(apiPermissionDTO.getId());
            apiPermissionDTO.setRoleList(roleList);
        }
        return apiPermissionList;
    }


}
