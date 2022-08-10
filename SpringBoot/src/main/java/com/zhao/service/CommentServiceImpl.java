package com.zhao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.ArticleService;
import com.zhao.api.CommentService;
import com.zhao.api.RedisService;
import com.zhao.api.UserService;
import com.zhao.constant.RedisPrefixConst;
import com.zhao.dto.*;
import com.zhao.exception.div.ServiceException;
import com.zhao.mapper.CommentMapper;
import com.zhao.pojo.Article;
import com.zhao.pojo.Comment;
import com.zhao.pojo.User;
import com.zhao.result.ResultInfo;
import com.zhao.utils.HTMLUtil;
import com.zhao.utils.UserUtil;
import com.zhao.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<CommentBackDTO> getUserReplyList(Integer pageNum, Integer pageSize, String nickname) {
        List<CommentBackDTO> userReplyList = this.baseMapper.getUserReplyList(pageNum, pageSize, nickname);
        for (CommentBackDTO commentBackDTO : userReplyList) {
            if (commentBackDTO.getReplyId() != null) {
                QueryWrapper<User> wrapper = new QueryWrapper<>();
                wrapper.eq("user_id",commentBackDTO.getReplyId());
                User user = userService.getOne(wrapper);
                commentBackDTO.setReplyNickname(user.getNickname());
            }

            if (commentBackDTO.getArticleId() != null) {
                QueryWrapper<Article> wrapper = new QueryWrapper<>();
                wrapper.eq("article_id",commentBackDTO.getArticleId());
                Article article = articleService.getOne(wrapper);
                commentBackDTO.setArticleTitle(article.getArticleTitle());
            }

        }
        Map<String, Object> likeMap = redisService.hGetAll(RedisPrefixConst.COMMENT_LIKE_COUNT);
        userReplyList.forEach(item -> item.setLikeCount((Integer) likeMap.get(item.getId().toString())));
        return userReplyList;
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId, Long current) {
        //转换页码查询评论下的回复
        List<ReplyDTO> replyDTOList = this.baseMapper.listRepliesByCommentId(commentId, (current - 1) * 5);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll("comment_like_count");
        // 封装点赞数据
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        return replyDTOList;
    }

    @Override
    public void saveCommentLike(Integer commentId) {
        User loginUser = UserUtil.getLoginUser(); // 拿到当前登录的用户
        // 判断是否点赞
        assert loginUser != null;
        String commentLikeKey = RedisPrefixConst.COMMENT_USER_LIKE + loginUser.getUserId();
        if (redisService.sIsMember(commentLikeKey, commentId)) {
            // 点过赞则删除评论id
            redisService.sRemove(commentLikeKey, commentId);
            // 评论点赞量-1
            redisService.hDecr(RedisPrefixConst.COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisService.sAdd(commentLikeKey, commentId);
            // 评论点赞量+1
            redisService.hIncr(RedisPrefixConst.COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    @Override
    public void saveComment(CommentVO commentVO) {
        //过滤html标签
        commentVO.setCommentContent(HTMLUtil.deleteCommentTag(commentVO.getCommentContent()));
        System.out.println(commentVO.getCommentContent());
        Comment comment = new Comment();
        //获取当前登录用户id
        User user = UserUtil.getLoginUser();
        assert user != null;
        if (user.isSilence()) {
            throw new ServiceException(ResultInfo.CODE_600,"亲，您已被禁言~");
        }
        Integer userId = user.getUserId();
        comment.setUserId(userId);
        if (commentVO.getArticleId() != null) {
            comment.setArticleId(commentVO.getArticleId());
        }
        if (commentVO.getCommentContent().length() > 2000) {
            throw new ServiceException(ResultInfo.CODE_600,"请保持字数在200字以内");
        }
        comment.setCommentContent(commentVO.getCommentContent());

        if (commentVO.getParentId() != null) {
            comment.setParentId(commentVO.getParentId());
        }
        if (commentVO.getReplyId() != null) {
            comment.setReplyId(commentVO.getReplyId());
        }
//        comment.setCreateTime(new DateTime());
        try {
            this.baseMapper.insert(comment);
        } catch (Exception e) {
            throw new ServiceException(ResultInfo.CODE_600, "回复失败，请重试");
        }
    }

    @Override
    public PageDTO<CommentDTO> listComments(Integer articleId, Integer current) {
        //查询文章评论量
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper .eq(articleId != null, "article_id", articleId)
                .isNull(articleId == null, "article_id")
                .isNull("parent_id")
                .eq("is_delete", 0);
        long commentCount = this.baseMapper.selectCount(wrapper);
        if (commentCount == 0) {
            return new PageDTO<>();
        }

        //查询评论集合
        List<CommentDTO> commentDTOList = this.baseMapper.listComments(articleId, (current - 1) * 10);

        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll("comment_like_count");

        //点赞量和评论量加入redis后在实现 现在不管
        List<Integer> commentIdList = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());


        if (commentIdList.size() <= 0) {
            throw new ServiceException(ResultInfo.CODE_600, "已经全部加载完成");
        }

        //根据评论id集合查询所有分页回复数据
        List<ReplyDTO> replyDTOList = this.baseMapper.listReplies(commentIdList);

        // 封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));

        //根据评论id查询回复量
        List<ReplyCountDTO> replyCountDTOList = this.baseMapper.listReplyCountByCommentId(commentIdList);

        //将回复量封装成评论id对应回复量的map
        Map<Integer, Integer> replyCountMap = new HashMap<>(16);
        for (ReplyCountDTO replyCountDTO : replyCountDTOList) {
            replyCountMap.put(replyCountDTO.getCommentId(), replyCountDTO.getReplyCount());
        }

        //将分页回复数据和回复量封装进对应的评论
        for (CommentDTO commentDTO : commentDTOList) {
            List<ReplyDTO> replyList = new ArrayList<>();
            for (ReplyDTO replyDTO : replyDTOList) {
                if (replyDTO.getParentId().equals(commentDTO.getId())) {
                    replyList.add(replyDTO);
                }
            }

            if (replyList.size() > 3) {
                for (int i = replyList.size() - 1; i > 2 ; i--) {
                    replyList.remove(i);
                }
            }

            // 封装顶级父评论点赞量
            commentDTO.setLikeCount((Integer) likeCountMap.get(commentDTO.getId().toString()));
            commentDTO.setReplyDTOList(replyList);
            commentDTO.setReplyCount(replyCountMap.get(commentDTO.getId()));
        }

        return new PageDTO<>(commentDTOList, commentCount);
    }


}
