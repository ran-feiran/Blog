package com.zhao.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.RoleApiService;
import com.zhao.mapper.RoleApiMapper;
import com.zhao.pojo.RoleApi;
import org.springframework.stereotype.Service;

@Service
public class RoleApiServiceImpl extends ServiceImpl<RoleApiMapper, RoleApi> implements RoleApiService {
}
