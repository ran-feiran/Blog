package com.zhao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {
}
