package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.UniqueViewDTO;
import com.zhao.pojo.UniqueView;

import java.util.List;

/**
 * 用户量统计
 *
 * @author ran-feiran
 * @date 2022/10/03
 */
public interface UniqueViewService extends IService<UniqueView> {

    /**
     * 获取7天用户量统计
     *
     * @return 用户量
     */
    List<UniqueViewDTO> listUniqueViews();
}
