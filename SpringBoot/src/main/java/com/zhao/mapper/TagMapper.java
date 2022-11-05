package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.ArticleBlogDTO;
import com.zhao.dto.TagBackDTO;
import com.zhao.dto.TagDTO;
import com.zhao.pojo.Tag;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> getTagListByArticleId(@Param("id") Integer id);


    List<TagDTO> selectTagList();


    /**
     * 查看标签对应的分类
     * @param tagId
     * @param current
     * @return
     */
    List<ArticleBlogDTO> listArticles(@Param("tagId") Integer tagId,
                                      @Param("current") Integer current);

    List<TagBackDTO> queryPageTags(@Param("condition")ConditionVO conditionVO);

    Integer queryArticleCountByTagId(@Param("tagId") Integer tagId);
}
