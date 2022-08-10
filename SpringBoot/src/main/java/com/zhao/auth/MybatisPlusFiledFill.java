package com.zhao.auth;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class MybatisPlusFiledFill implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("==start insert ······==");
        this.setFieldValByName("createTime",new DateTime(),metaObject);
        this.setFieldValByName("updateTime",new DateTime(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("==start update ······==");
        this.setFieldValByName("updateTime",new DateTime(),metaObject);
    }
}
