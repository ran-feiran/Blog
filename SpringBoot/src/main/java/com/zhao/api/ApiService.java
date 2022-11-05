package com.zhao.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.ApiBackDTO;
import com.zhao.dto.ApiPermissionDTO;
import com.zhao.pojo.Api;
import com.zhao.vo.AnonymousVO;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.ResourceSaveVO;

import java.util.List;

/**
 * api服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface ApiService extends IService<Api> {


    /**
     * 得到返回的资源信息
     *
     * @param conditionVO 条件签证官
     * @return {@link List}<{@link ApiBackDTO}>
     */
    List<ApiBackDTO> getResourcesInfoBack(ConditionVO conditionVO);

    /**
     * 更新匿名通过id
     *
     * @param anonymousVO 匿名签证官
     */
    void updateAnonymousById(AnonymousVO anonymousVO);

    /**
     * 删除资源信息通过id
     *
     * @param id id
     */
    void deleteResourceInfoById(Integer id);

    /**
     * 添加或编辑资源
     *
     * @param resourceSaveVO 资源节约签证官
     */
    void addOrEditResource(ResourceSaveVO resourceSaveVO);


    /**
     * 得到所有api许可
     *
     * @return {@link List}<{@link ApiPermissionDTO}>
     */
    List<ApiPermissionDTO> getAllApiPermission();
}
