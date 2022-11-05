package com.zhao.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.PageDTO;
import com.zhao.dto.UserLoginDTO;
import com.zhao.pojo.UserLogin;
import com.zhao.vo.ConditionVO;

/**
 * 用户登录服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface UserLoginService extends IService<UserLogin> {

    /**
     * 得到用户信息列表
     *
     * @param conditionVO 条件签证官
     * @return {@link PageDTO}<{@link UserLogin}>
     */
    PageDTO<UserLoginDTO> getUserInfoList (ConditionVO conditionVO);
}
