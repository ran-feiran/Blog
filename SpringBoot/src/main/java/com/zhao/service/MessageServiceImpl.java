package com.zhao.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.jdbc.StringUtils;
import com.zhao.api.MessageService;
import com.zhao.mapper.MessageMapper;
import com.zhao.pojo.Message;
import com.zhao.utils.IpUtil;
import com.zhao.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService  {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    private HttpServletRequest request;

    @Override
    public IPage<Message> getMessageList(Integer current, Integer size, String nickname) {
        Page<Message> page = new Page<>(current, size);
        QueryWrapper<Message> wrapper = null;
        if (!StringUtils.isNullOrEmpty(nickname)) {
            wrapper = new QueryWrapper<>();
            wrapper.like("nickname", nickname);
        }
        return messageMapper.selectPage(page, wrapper);
    }

    @Override
    public void saveMessage(MessageVO messageVO) {
        String ipAddr = IpUtil.getIp(request);
        String ipSource = IpUtil.getIpSource(ipAddr);
        Message message = new Message();
        message.setAvatar(messageVO.getAvatar());
        message.setNickname(messageVO.getNickname());
        message.setCreateTime(new DateTime());
        message.setMessageContent(messageVO.getMessageContent());
        message.setIpAddress(ipAddr);
        message.setIpSource(ipSource);
        message.setTime(messageVO.getTime());
        messageMapper.insert(message);
    }
}
