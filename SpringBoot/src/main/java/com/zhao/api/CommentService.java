package com.zhao.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.CommentBackDTO;
import com.zhao.dto.CommentDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.ReplyDTO;
import com.zhao.pojo.Comment;
import com.zhao.vo.CommentVO;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.ReviewVO;

import java.util.List;

/**
 * 评论服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface CommentService extends IService<Comment> {

    /**
     * 得到用户回复列表
     *
     * @param conditionVO 条件签证官
     * @return {@link PageDTO}<{@link CommentBackDTO}>
     */
    PageDTO<CommentBackDTO> getUserReplyList (ConditionVO conditionVO);


    /**
     *得到评论列表
     * @param articleId
     * @param current
     * @return
     */
    PageDTO<CommentDTO> listComments(Integer articleId, Integer current);

    /**
     * 保存前台发送过来的评论
     * @param commentVO
     */
    void saveComment(CommentVO commentVO);

    /**
     * 通过评论id获取最新的回复
     *
     * @param commentId 评论id
     * @param current   当前
     * @return {@link List}<{@link ReplyDTO}>
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId, Long current);

    /**
     * 保存评论点赞
     * @param commentId
     */
    void saveCommentLike(Integer commentId);

    /**
     * 更新评论审核
     *
     * @param reviewVO 审查签证官
     */
    void updateCommentReview(ReviewVO reviewVO);
}
