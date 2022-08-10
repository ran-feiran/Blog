package com.zhao.api;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.CommentBackDTO;
import com.zhao.dto.CommentDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.ReplyDTO;
import com.zhao.pojo.Comment;
import com.zhao.vo.CommentVO;

import java.util.List;

public interface CommentService extends IService<Comment> {

    /**
     * 得到用户回复列表
     * @param pageNum
     * @param pageSize
     * @param nickname
     * @return
     */
    List<CommentBackDTO> getUserReplyList (Integer pageNum, Integer pageSize , String nickname);


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


    List<ReplyDTO> listRepliesByCommentId(Integer commentId, Long current);


    /**
     * 保存评论点赞
     * @param commentId
     */
    void saveCommentLike(Integer commentId);

}
