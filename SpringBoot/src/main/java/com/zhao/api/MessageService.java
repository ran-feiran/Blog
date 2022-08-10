package com.zhao.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.pojo.Message;
import com.zhao.vo.MessageVO;

public interface MessageService extends IService<Message> {


    /**
     * 查看留言列表
     * @param current
     * @param size
     * @param nickname
     * @return
     */
    IPage<Message> getMessageList(Integer current, Integer size, String nickname);

    void saveMessage(MessageVO messageVO);

}
