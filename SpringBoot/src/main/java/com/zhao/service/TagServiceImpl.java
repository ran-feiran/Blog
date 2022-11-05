package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.TagService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.TagBackDTO;
import com.zhao.mapper.TagMapper;
import com.zhao.pojo.Tag;
import com.zhao.vo.ConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<ArticleBlogDTO> listArticles(Integer tagId, Integer current) {
        return tagMapper.listArticles(tagId, current);
    }

    @Override
    public PageDTO<TagBackDTO> queryPageTags(ConditionVO conditionVO) {
        Long count = tagMapper.selectCount(null);
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        List<TagBackDTO> tagBackDTOS = tagMapper.queryPageTags(conditionVO);
        tagBackDTOS.forEach(item -> {
            item.setArticleCount(tagMapper.queryArticleCountByTagId(item.getTagId()));
        });
        return new PageDTO<>(tagBackDTOS, count);
    }

    @Override
    public List<Tag> searchTags(String keywords) {
        return tagMapper.selectList(new LambdaQueryWrapper<Tag>().like(Tag::getTagName, keywords));
    }
}
