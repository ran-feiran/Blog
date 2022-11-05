package com.zhao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.dto.FriendLinkBackDTO;
import com.zhao.pojo.FriendLink;
import com.zhao.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendLinkMapper extends BaseMapper<FriendLink> {

    /**
     * 让朋友链接列表
     *
     * @param conditionVO 条件签证官
     * @return {@link List}<{@link FriendLinkBackDTO}>
     */
    List<FriendLinkBackDTO> getFriendLinkList(@Param("condition") ConditionVO conditionVO);
}
