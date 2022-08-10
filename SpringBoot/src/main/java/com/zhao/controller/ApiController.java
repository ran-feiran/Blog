package com.zhao.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.ApiService;
import com.zhao.dto.ApiBackDTO;
import com.zhao.exception.div.ServiceException;
import com.zhao.mapper.RoleApiMapper;
import com.zhao.pojo.Api;
import com.zhao.pojo.RoleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private RoleApiMapper roleApiMapper;

    @GetMapping("/api/listApiInfoBack")
    public Result listApiInfoBack(@RequestParam(value = "apiId",required = false) Integer apiId) {
        List<ApiBackDTO> apiBackDTOS = null;
        try {
            apiBackDTOS = apiService.listApiInfoBack(apiId);
            QueryWrapper<Api> wrapper = new QueryWrapper<>();
            wrapper.eq("api_id", 15);
            ApiBackDTO apiBackDTO = new ApiBackDTO();
            Api api = apiService.getOne(wrapper);
            BeanUtil.copyProperties(api,apiBackDTO,false);
            apiBackDTOS.add(apiBackDTO);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600, "查询API失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("apiListInfo", apiBackDTOS);
        return Result.success(map, "查询成功");
    }

    @GetMapping("/role/getAssignedApiIdByUserRoleId")
    public Result getAssignedApiIdByUserRoleId(@RequestParam("actionId") Integer actionId) {
        QueryWrapper<RoleApi> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", actionId);
        List<RoleApi> roleApis = null;
        try {
            roleApis = roleApiMapper.selectList(wrapper);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600, "查询角色API失败");
        }
        List<Integer> apiIdList = null;
        if (roleApis != null) {
            apiIdList = roleApis.
                    stream().
                    map(res -> res.getApiId()).
                    collect(Collectors.toList());
        }
        Map<String, Object> map = new HashMap<>();
        if (apiIdList.size() == 0) {
            return Result.error(ResultInfo.CODE_600, "该角色无任何权限");
        }
        map.put("apiIdList", apiIdList);
        return Result.success(map, "查询成功");
    }


    @PostMapping("/role/saveRolePermissionList/{roleId}")
    private Result saveRolePermissionList(@PathVariable("roleId") Integer roleId,
                                          @RequestBody List<Integer> apiList) {
        try {
            apiService.saveRolePermissionList(roleId, apiList);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600, "保存API失败");
        }
        return Result.success();
    }
}
