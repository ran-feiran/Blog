package com.zhao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.ApiBackDTO;
import com.zhao.dto.ApiPermissionDTO;
import com.zhao.pojo.Api;
import com.zhao.vo.AnonymousVO;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ApiMapper extends BaseMapper<Api> {


    /**
     * 取回资源信息
     *
     * @param conditionVO 条件签证官
     * @return {@link List}<{@link ApiBackDTO}>
     */
    List<ApiBackDTO> getResourcesInfoBack(@Param("condition") ConditionVO conditionVO);

    /**
     * 得到所有api许可
     *
     * @return {@link List}<{@link ApiPermissionDTO}>
     */
    List<ApiPermissionDTO> getAllApiPermission();

    /**
     * 更新匿名通过id
     *
     * @param anonymousVO 匿名签证官
     */
    void updateAnonymousById(@Param("anonymous") AnonymousVO anonymousVO);
}
