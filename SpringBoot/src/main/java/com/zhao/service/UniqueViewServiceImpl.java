package com.zhao.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.zhao.api.RedisService;
import com.zhao.api.UniqueViewService;
import com.zhao.dto.UniqueViewDTO;
import com.zhao.dto.UserAreaDTO;
import com.zhao.mapper.UniqueViewMapper;
import com.zhao.pojo.UniqueView;
import com.zhao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.zhao.constant.CommonConst.*;
import static com.zhao.constant.RedisPrefixConst.*;
import static com.zhao.enums.ZoneEnum.SHANGHAI;


@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewMapper, UniqueView> implements UniqueViewService {

    @Autowired
    private UniqueViewMapper uniqueViewMapper;

    @Override
    public List<UniqueViewDTO> listUniqueViews() {
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        return uniqueViewMapper.listUniqueViews(startTime, endTime);
    }




}
