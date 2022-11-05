package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.PageBackDTO;
import com.zhao.pojo.Page;
import com.zhao.vo.PageVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PageMapper extends BaseMapper<Page> {

    /**
     * 列表页面
     *
     * @return {@link List}<{@link PageBackDTO}>
     */
    List<PageBackDTO> listPages();

    /**
     * 获得页面列表
     *
     * @return {@link List}<{@link PageVO}>
     */
    List<PageVO> getPageList();
}
