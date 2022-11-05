package com.zhao.controller;


import com.zhao.annotations.AccessLimit;
import com.zhao.annotations.OptLog;
import com.zhao.api.CommentService;
import com.zhao.dto.CommentBackDTO;
import com.zhao.dto.CommentDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.ReplyDTO;
import com.zhao.result.ResultStandby;
import com.zhao.vo.CommentVO;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.ReviewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.zhao.constant.OptTypeConst.SAVE;
import static com.zhao.constant.OptTypeConst.UPDATE;
import static com.zhao.enums.StatusCodeEnum.SUCCESS;
import static com.zhao.result.ResultStandby.success;

/**
 * 评论控制器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@RestController
@Api(tags = "评论模块")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "评论点赞")
    @OptLog(optType = SAVE)
    @PostMapping("/like/{commentId}")
    public ResultStandby<?> saveCommentLike(@PathVariable("commentId") Integer commentId) {
        commentService.saveCommentLike(commentId);
        return success();
    }

    @ApiOperation(value = "更新id对应父评论下的子评论")
    @GetMapping("/comments/replies")
    public ResultStandby<List<ReplyDTO>> reloadReply(
                                @RequestParam("commentId") Integer commentId,
                                @RequestParam("current") Long current) {
        return success(commentService.listRepliesByCommentId(commentId, current), SUCCESS.getDesc());
    }

    @ApiOperation(value = "保存评论")
    @OptLog(optType = SAVE)
    @PostMapping("/comments")
    public ResultStandby<?> saveComments(@RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return success();
    }

    @ApiOperation(value = "获取评论列表")
    @GetMapping("/comments")
    public ResultStandby<PageDTO<CommentDTO>> listComments(
                                @RequestParam(value = "current", required = false) Integer current,
                                @RequestParam(value = "articleId", required = false) Integer articleId) {
        return success(commentService.listComments(articleId, current), SUCCESS.getDesc());
    }

    @ApiOperation(value = "获取评论列表(后台)")
    @AccessLimit(seconds = 60, maxCount = 5, desc = "请求过于频繁，请稍候再试") // 一分钟最大评论五次
    @GetMapping("/getUserCommentList")
    public ResultStandby<PageDTO<CommentBackDTO>> getUserCommentList(ConditionVO conditionVO) {
        return success(commentService.getUserReplyList(conditionVO),SUCCESS.getDesc());
    }

    @ApiOperation(value = "逻辑删除评论")
    @OptLog(optType = UPDATE)
    @DeleteMapping("/del/batch")
    public ResultStandby<?> delComments(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return success();
    }

    @ApiOperation(value = "评论审核")
    @OptLog(optType = UPDATE)
    @PutMapping("/review")
    public ResultStandby<?> updateCommentReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentReview(reviewVO);
        return success();
    }
}
