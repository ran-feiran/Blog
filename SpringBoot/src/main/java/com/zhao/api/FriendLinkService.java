package com.zhao.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.dto.FriendLinkBackDTO;
import com.zhao.dto.PageDTO;
import com.zhao.pojo.FriendLink;
import com.zhao.vo.ConditionVO;

/**
 * 友链服务
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
public interface FriendLinkService extends IService<FriendLink> {


    /**
     * 获取友情链接列表
     *
     * @param conditionVO 条件签证官
     * @return {@link PageDTO}<{@link FriendLink}>
     */
    PageDTO<FriendLinkBackDTO> getFriendLinkList(ConditionVO conditionVO);

    /**
     * 添加或编辑朋友联系
     *
     * @param friendLink 朋友联系
     */
    void addOrEditFriendLink(FriendLinkBackDTO friendLink);
}
