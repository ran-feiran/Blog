package com.zhao.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.api.RedisService;
import com.zhao.dto.CommentDTO;
import com.zhao.dto.PageDTO;
import com.zhao.dto.ReplyDTO;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import com.zhao.api.CommentService;
import com.zhao.dto.CommentBackDTO;
import com.zhao.exception.div.ServiceException;
import com.zhao.pojo.Comment;
import com.zhao.vo.CommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "评论模块")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;



    @PostMapping("/{commentId}/like")
    public Result saveCommentLike(@PathVariable("commentId") Integer commentId) {
        commentService.saveCommentLike(commentId);
        return Result.success();
    }


    @GetMapping("/comments/replies")
    public Result reloadReply(@RequestParam("commentId") Integer commentId,
                              @RequestParam("current") long current) {
        List<ReplyDTO> replyDTOS = commentService.listRepliesByCommentId(commentId, current);
        Map<String, Object> map = new HashMap<>();
        map.put("data",replyDTOS);
        return Result.success(map, "");
    }


    @PostMapping("/comments")
    public Result saveComments(@RequestBody CommentVO commentVO) {
        commentService.saveComment(commentVO);
        return Result.success();
    }


    @GetMapping("/comments")
    public Result listComments(@RequestParam(value = "current", required = false) Integer current,
                               @RequestParam(value = "articleId", required = false) Integer articleId) {
        // 如果有articleId证明是在文章下面评论，如果没有证明是在友链下面评论
        PageDTO<CommentDTO> pageDTO = commentService.listComments(articleId, current);
        if (pageDTO == null) {
            return Result.error(ResultInfo.CODE_600, "还没有人发表谈论~");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("recordList",pageDTO.getRecordList());
        System.out.println("23333:"+Arrays.toString(pageDTO.getRecordList().toArray()));
        map.put("count",pageDTO.getCount());
        return Result.success(map, "");
    }


    @GetMapping("/getUserCommentList")
    @ApiOperation(value = "分页获取用户评论列表")
    public Result getUserCommentList(@RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam("nickname") String nickname) {
        pageNum = (pageNum - 1) * pageSize;
        List<CommentBackDTO> userReplyList = commentService.getUserReplyList(pageNum, pageSize, nickname);
        long total = commentService.count();
        Map<String, Object> map = new HashMap<>();
        map.put("userReplyList",userReplyList);
        map.put("total",total);
        return Result.success(map,"用户评论列表加载完成");
    }


    @PostMapping("/delComments")
    @ApiOperation(value = "删除用户评论")
    public Result delComments(@RequestBody List<Integer> ids) {  // 这里需要优化
        try {
            commentService.removeByIds(ids);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600,"删除失败");
        }
        return Result.success(null,"删除成功");
    }
}
