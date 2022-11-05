package com.zhao.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.ApiService;
import com.zhao.authority.MyFilterInvocationSecurityMetadataSource;
import com.zhao.dto.ApiBackDTO;
import com.zhao.dto.ApiPermissionDTO;
import com.zhao.mapper.ApiMapper;
import com.zhao.pojo.Api;
import com.zhao.utils.BeanCopyUtil;
import com.zhao.vo.AnonymousVO;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.ResourceSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

    @Autowired
    private ApiMapper apiMapper;
    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    @Override
    public List<ApiBackDTO> getResourcesInfoBack(ConditionVO conditionVO) {
        return apiMapper.getResourcesInfoBack(conditionVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAnonymousById(AnonymousVO anonymousVO) {
        apiMapper.updateAnonymousById(anonymousVO);
        myFilterInvocationSecurityMetadataSource.clearDataSource();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteResourceInfoById(Integer id) {
        apiMapper.deleteById(id);
        myFilterInvocationSecurityMetadataSource.clearDataSource();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrEditResource(ResourceSaveVO resourceSaveVO) {
        saveOrUpdate(BeanCopyUtil.copyObject(resourceSaveVO, Api.class));
        myFilterInvocationSecurityMetadataSource.clearDataSource();
    }

    @Override
    public List<ApiPermissionDTO> getAllApiPermission() {
        return apiMapper.getAllApiPermission();
    }


}
