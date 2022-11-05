package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.CommentBackDTO;
import com.zhao.dto.CommentDTO;
import com.zhao.dto.ReplyCountDTO;
import com.zhao.dto.ReplyDTO;
import com.zhao.pojo.Comment;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {


    List<CommentBackDTO> getUserReplyList (@Param("condition") ConditionVO conditionVO);


    /**
     * 查看顶级父类评论
     * @param articleId
     * @param current
     * @return 顶级评论
     */
    List<CommentDTO> listComments(@Param("articleId") Integer articleId,
                                  @Param("current")Integer current);

    /**
     * 查看评论id集合下的回复
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 根据评论id查询回复总量
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 查看当前评论id下的回复
     * @param commentId
     * @param current
     * @return
     */
    List<ReplyDTO> listRepliesByCommentId(@Param("commentId") Integer commentId,
                                          @Param("current") Long current);

    /**
     * 更新评论评论
     *
     * @param idList   id列表
     * @param isReview 是审查
     */
    void updateCommentReview(@Param("ids") List<Integer> idList,
                             @Param("isReview") Integer isReview);
}
