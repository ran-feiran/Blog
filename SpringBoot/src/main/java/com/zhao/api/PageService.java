package com.zhao.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.PageBackDTO;
import com.zhao.pojo.Page;
import com.zhao.vo.PageVO;

import java.util.List;

public interface PageService extends IService<Page> {

    /**
     * 列表页面
     *
     * @return {@link PageBackDTO}
     */
    List<PageBackDTO> listPages();


    /**
     * 获得页面列表
     *
     * @return {@link List}<{@link PageVO}>
     */
    List<PageVO> getPageList();

    /**
     * 添加或编辑页面
     *
     * @param pageVO 页签证官
     */
    void addOrEditPage(PageVO pageVO);
}
