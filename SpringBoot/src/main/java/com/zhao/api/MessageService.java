package com.zhao.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.MessageDTO;
import com.zhao.dto.PageDTO;
import com.zhao.pojo.Message;
import com.zhao.vo.ConditionVO;
import com.zhao.vo.MessageVO;
import com.zhao.vo.ReviewVO;

/**
 * 留言服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface MessageService extends IService<Message> {


    /**
     * 查看留言列表
     *
     * @param conditionVO@return
     */
    PageDTO<MessageDTO> getMessageList(ConditionVO conditionVO);

    /**
     * 保存信息
     *
     * @param messageVO 留言对象
     */
    void saveMessage(MessageVO messageVO);

    void updateMessageReview(ReviewVO reviewVO);
}
