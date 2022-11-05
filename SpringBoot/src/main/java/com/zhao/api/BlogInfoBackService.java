package com.zhao.api;

import com.zhao.dto.BlogInfoBackDTO;
import com.zhao.dto.UserAreaDTO;
import com.zhao.vo.ConditionVO;

import java.util.List;

/**
 * 后台服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface BlogInfoBackService {

    /**
     * 获取后台首页信息
     *
     * @return {@link BlogInfoBackDTO}
     */
    BlogInfoBackDTO getBlogBackInfo();

    /**
     * 获取用户列表区域
     *
     * @param conditionVO 条件签证官
     * @return {@link List}<{@link UserAreaDTO}>
     */
    List<UserAreaDTO> listUserAreas(ConditionVO conditionVO);


    /**
     * 上传访客信息
     */
    void report();

    /**
     * @return {@link String}
     */
    String loadLoginPage();
}
