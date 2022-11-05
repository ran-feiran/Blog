package com.zhao.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.zhao.api.RedisService;
import com.zhao.api.UserService;
import com.zhao.dto.UserAreaDTO;
import com.zhao.mapper.UniqueViewMapper;
import com.zhao.pojo.UniqueView;
import com.zhao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.zhao.constant.CommonConst.*;
import static com.zhao.constant.RedisPrefixConst.*;
import static com.zhao.enums.ZoneEnum.SHANGHAI;

@Component
public class MyScheduledHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UniqueViewMapper uniqueViewMapper;


    // 每一个小时执行一次此方法
    @Scheduled(cron = "0 0 * * * ?")
    public void statisticalUserArea() {
        // 统计用户地域分布
        Map<String, Long> userAreaMap = userService.list(new LambdaQueryWrapper<User>()
                        .select(User::getIpSource))
                .stream()
                .map(item -> {
                    if (!StringUtils.isNullOrEmpty(item.getIpSource())) {
                        return item.getIpSource().substring(0, 3)
                                .replaceAll(PROVINCE, "")
                                .replaceAll(CITY, "");
                    }
                    return UNKNOWN;
                })
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
        // 转换格式
        List<UserAreaDTO> userAreaList = userAreaMap.entrySet().stream()
                .map(item -> UserAreaDTO.builder()
                        .name(item.getKey())
                        .value(item.getValue())
                        .build())
                .collect(Collectors.toList());
        redisService.set(USER_AREA, JSON.toJSONString(userAreaList));
    }

    /**
     * 保存每天的用户访问量
     */
    // 每天00：00：00执行一次此方法
    @Scheduled(cron = " 0 0 0 * * ?", zone = "Asia/Shanghai")
    public void saveUniqueView() {
        // 获取每天用户量
        Long count = redisService.sSize(UNIQUE_VISITOR);
        // 获取昨天日期插入数据, 每天更新昨日访问数据
        UniqueView uniqueView = UniqueView.builder()
                .updateTime(LocalDateTimeUtil.offset(LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())), 0, ChronoUnit.DAYS))
                .createTime(LocalDateTimeUtil.offset(LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())), -1, ChronoUnit.DAYS))
                .viewsCount(Optional.of(count.intValue()).orElse(0))
                .build();
        // 插入昨日访问量
        uniqueViewMapper.insert(uniqueView);
    }


    /**
     *清楚前天的访客记录，记录下一天
     *
     */
    // 每天00：01：00执行一次此方法
    @Scheduled(cron = " 0 1 0 * * ?", zone = "Asia/Shanghai")
    public void clear() {
        // 清空redis访客记录
        redisService.del(UNIQUE_VISITOR);
        // 清空redis访客区域统计
        redisService.del(VISITOR_AREA);
    }
}
