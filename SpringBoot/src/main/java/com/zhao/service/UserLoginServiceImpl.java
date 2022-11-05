package com.zhao.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.UserLoginService;
import com.zhao.dto.PageDTO;
import com.zhao.dto.UserLoginDTO;
import com.zhao.mapper.UserLoginMapper;
import com.zhao.pojo.UserLogin;
import com.zhao.vo.ConditionVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserLoginServiceImpl extends ServiceImpl<UserLoginMapper, UserLogin> implements UserLoginService {

    @Override
    public PageDTO<UserLoginDTO> getUserInfoList(ConditionVO conditionVO) {
        Long count = this.baseMapper.selectCount(null);
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        return new PageDTO<>(this.baseMapper.getUserInfoList(conditionVO), count);
    }
}
