package com.zhao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.MessageDTO;
import com.zhao.pojo.Message;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    List<MessageDTO> getMessageList(@Param("condition") ConditionVO conditionVO);

    void updateMessageReview(@Param("ids") List<Integer> idList,@Param("isReview") Integer isReview);
}
