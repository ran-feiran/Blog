package com.zhao.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.CommentService;
import com.zhao.api.RedisService;
import com.zhao.api.WebsiteConfigService;
import com.zhao.dto.*;
import com.zhao.exception.ServiceException;
import com.zhao.mapper.CommentMapper;
import com.zhao.pojo.Comment;
import com.zhao.pojo.User;
import com.zhao.utils.HTMLUtil;
import com.zhao.utils.UserUtil;
import com.zhao.vo.CommentVO;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.ReviewVO;
import com.zhao.vo.WebsiteConfigVO;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.zhao.constant.CommonConst.*;
import static com.zhao.constant.MQPrefixConst.FANOUT_EMAIL_EXCHANGE;
import static com.zhao.constant.RedisPrefixConst.COMMENT_LIKE_COUNT;
import static com.zhao.constant.RedisPrefixConst.COMMENT_USER_LIKE;
import static com.zhao.enums.StatusCodeEnum.FAIL;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private WebsiteConfigService websiteConfigService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public PageDTO<CommentBackDTO> getUserReplyList(ConditionVO conditionVO) {
        // 查询所有评论
        Long count = this.baseMapper.selectCount(null);
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        // 查询所有评论
        conditionVO.setCurrent((conditionVO.getCurrent() - 1) * conditionVO.getSize());
        List<CommentBackDTO> userReplyList = this.baseMapper.getUserReplyList(conditionVO);
        return new PageDTO<>(userReplyList, count);
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId, Long current) {
        //转换页码查询评论下的回复
        List<ReplyDTO> replyDTOList = this.baseMapper.listRepliesByCommentId(commentId, (current - 1) * 5);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        // 封装点赞数据
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        return replyDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCommentLike(Integer commentId) {
        User loginUser = UserUtil.getLoginUser(); // 拿到当前登录的用户
        // 判断是否点赞
        assert loginUser != null;
        String commentLikeKey = COMMENT_USER_LIKE + loginUser.getUserId();
        if (redisService.sIsMember(commentLikeKey, commentId)) {
            // 点过赞则删除评论id
            redisService.sRemove(commentLikeKey, commentId);
            // 评论点赞量-1
            redisService.hDecr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisService.sAdd(commentLikeKey, commentId);
            // 评论点赞量+1
            redisService.hIncr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCommentReview(ReviewVO reviewVO) {
        this.baseMapper.updateCommentReview(reviewVO.getIdList(), reviewVO.getIsReview());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveComment(CommentVO commentVO) {
        //过滤html标签
        commentVO.setCommentContent(HTMLUtil.deleteCommentTag(commentVO.getCommentContent()));
        Comment comment = new Comment();
        // 判断是否需要审核
        WebsiteConfigVO websiteConfig = websiteConfigService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        //获取当前登录用户id
        User user = UserUtil.getLoginUser();
        assert user != null;
        if (user.getIsSilence() == 1) {
            throw new ServiceException(FAIL.getCode(),"账号禁用中！");
        }
        Integer userId = user.getUserId();
        comment.setUserId(userId);
        if (commentVO.getArticleId() != null) {
            comment.setArticleId(commentVO.getArticleId());
            comment.setType(1);
        } else {
            comment.setType(2);
        }
        comment.setCommentContent(commentVO.getCommentContent().isEmpty() ? null : commentVO.getCommentContent());
        comment.setParentId(commentVO.getParentId() == null ? null : commentVO.getParentId());
        comment.setReplyId(commentVO.getReplyId() == null ? null : commentVO.getReplyId());
        comment.setIsReview(isReview == TRUE ? FALSE : TRUE);
        try {
            this.baseMapper.insert(comment);
        } catch (Exception e) {
            throw new ServiceException(FAIL.getCode(), FAIL.getDesc());
        }
        Integer isEmailNotice = websiteConfig.getIsEmailNotice();
        if (isEmailNotice == TRUE) {
            if (!user.getEmail().isEmpty()) {
                EmailDTO emailDTO = EmailDTO.builder()
                        .email(user.getEmail())
                        .subject(COMMENT_NOTICE)
                        .content("您在" + EMAIL_SUBJECT +
                                "的评论为：<b style='color:orange'>" + comment.getCommentContent()+ "</b>" + "<br/>" +
                                "评论时间为：<b>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</b>")
                        .build();
                // 发送到消息队列中
                rabbitTemplate.convertAndSend(FANOUT_EMAIL_EXCHANGE, "*", new org.springframework.amqp.core.Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
            }
        }
    }

    @Override
    public PageDTO<CommentDTO> listComments(Integer articleId, Integer current) {
        //查询文章评论量
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper .eq(articleId != null, "article_id", articleId)
                .isNull(articleId == null, "article_id")
                .isNull("parent_id")
                .eq("is_delete", 0)
                .eq("is_review", 1);
        long commentCount = this.baseMapper.selectCount(wrapper);
        if (commentCount == 0) {
            return new PageDTO<>(new ArrayList<>(),0);
        }
        //查询评论集合
        List<CommentDTO> commentDTOList = this.baseMapper.listComments(articleId, (current - 1) * 10);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        //点赞量和评论量加入redis后在实现 现在不管
        List<Integer> commentIdList = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        if (commentIdList.size() <= 0) {
            throw new ServiceException(FAIL.getCode(), "已经全部加载完成");
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
            // 封装顶级父评论点赞量
            commentDTO.setLikeCount((Integer) likeCountMap.get(commentDTO.getId().toString()));
            commentDTO.setReplyDTOList(replyList);
            commentDTO.setReplyCount(replyCountMap.get(commentDTO.getId()));
        }
        return new PageDTO<>(commentDTOList, commentCount);
    }


}
