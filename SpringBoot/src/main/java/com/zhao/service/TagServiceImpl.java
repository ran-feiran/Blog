package com.zhao.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.TagService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.mapper.TagMapper;
import com.zhao.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Autowired
    TagMapper tagMapper;

    @Override
    public List<ArticleBlogDTO> listArticles(Integer tagId, Integer current) {
        List<ArticleBlogDTO> articleBlogDTOS = tagMapper.listArticles(tagId, current);
        for (ArticleBlogDTO articleBlogDTO : articleBlogDTOS) {
            List<Tag> tagList = tagMapper.getTagListByArticleId(articleBlogDTO.getArticleId());
            articleBlogDTO.setTagList(tagList);
        }
        return articleBlogDTOS;
    }
}
