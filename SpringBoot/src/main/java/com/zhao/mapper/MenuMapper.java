package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.MenuDTO;
import com.zhao.pojo.Menu;
import com.zhao.vo.MenuHiddenVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获得角色名菜单列表
     *
     * @param roleName 角色名
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> getMenuListByRoleName(@Param("roleName") String roleName);

    /**
     * 更新菜单隐藏状态
     *
     * @param hiddenVO 隐藏签证官
     */
    void updateHiddenById(@Param("hidden") MenuHiddenVO hiddenVO);


}
