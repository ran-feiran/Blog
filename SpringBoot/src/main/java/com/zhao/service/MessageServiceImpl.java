package com.zhao.service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.api.MessageService;
import com.zhao.api.WebsiteConfigService;
import com.zhao.dto.EmailDTO;
import com.zhao.dto.MessageDTO;
import com.zhao.dto.PageDTO;
import com.zhao.exception.ServiceException;
import com.zhao.mapper.MessageMapper;
import com.zhao.pojo.Message;
import com.zhao.pojo.User;
import com.zhao.utils.IpUtil;
import com.zhao.utils.UserUtil;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.MessageVO;
import com.zhao.vo.ReviewVO;
import com.zhao.vo.WebsiteConfigVO;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static com.zhao.constant.CommonConst.*;
import static com.zhao.constant.MQPrefixConst.FANOUT_EMAIL_EXCHANGE;
import static com.zhao.enums.StatusCodeEnum.FAIL;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService  {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private WebsiteConfigService websiteConfigService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMessageReview(ReviewVO reviewVO) {
        messageMapper.updateMessageReview(reviewVO.getIdList(),reviewVO.getIsReview());
    }

    @Override
    public PageDTO<MessageDTO> getMessageList(ConditionVO conditionVO) {
        // 查询留言数量
        Long count = messageMapper.selectCount(null);
        if (count == null || count == 0) {
            return new PageDTO<>(new ArrayList<>(), 0);
        }
        // 查询留言列表
        conditionVO.setCurrent((conditionVO.getCurrent() - 1 ) * conditionVO.getSize());
        return new PageDTO<>(messageMapper.getMessageList(conditionVO), count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMessage(MessageVO messageVO) {
        WebsiteConfigVO websiteConfig = websiteConfigService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsMessageReview(); // 判断是否需要审核
        String ipAddress = IpUtil.getIp(request);
        String ipSource = IpUtil.getIpSource(ipAddress);
        Message message = Message
                                .builder()
                                .isReview(isReview == TRUE ? FALSE : TRUE) // 开启管理员审核
                                .avatar(messageVO.getAvatar())
                                .messageContent(messageVO.getMessageContent())
                                .time(messageVO.getTime())
                                .nickname(messageVO.getNickname())
                                .ipAddress(ipAddress)
                                .ipSource(ipSource)
                                .build();
        messageMapper.insert(message);
        Integer isEmailNotice = websiteConfig.getIsEmailNotice();
        User loginUser = UserUtil.getLoginUser();
        if (isEmailNotice == TRUE && Objects.nonNull(loginUser)) {
            if (!loginUser.getEmail().isEmpty()) {
                EmailDTO emailDTO = EmailDTO.builder()
                        .email(loginUser.getEmail())
                        .subject(MESSAGE_NOTICE)
                        .content("您在" + EMAIL_SUBJECT +
                                "的留言为：<b style='color:orange'>" + messageVO.getMessageContent() + "</b>" + "<br/>" +
                                "留言时间为：<b>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</b>")
                        .build();
                // 发送到消息队列中
                rabbitTemplate.convertAndSend(FANOUT_EMAIL_EXCHANGE, "*", new org.springframework.amqp.core.Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
            }
        }
    }
}
