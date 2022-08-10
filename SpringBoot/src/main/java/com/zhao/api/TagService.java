package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.pojo.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<ArticleBlogDTO> listArticles(Integer tagId, Integer current);
}
