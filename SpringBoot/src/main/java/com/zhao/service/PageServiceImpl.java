package com.zhao.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.PageService;
import com.zhao.dto.PageBackDTO;
import com.zhao.mapper.PageMapper;
import com.zhao.pojo.Page;
import com.zhao.utils.BeanCopyUtil;
import com.zhao.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements PageService {

    @Autowired
    private PageMapper pageMapper;

    @Override
    public List<PageBackDTO> listPages() {
        return pageMapper.listPages();
    }

    @Override
    public List<PageVO> getPageList() {
        return pageMapper.getPageList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrEditPage(PageVO pageVO) {
        saveOrUpdate(BeanCopyUtil.copyObject(pageVO, Page.class));
    }
}
