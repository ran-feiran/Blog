package com.zhao.handler;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * pojo属性填充处理者
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Log4j2
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("==start insert ······===");
        this.setFieldValByName("createTime",new DateTime(),metaObject);
        this.setFieldValByName("updateTime",new DateTime(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("==start update ······===");
        this.setFieldValByName("updateTime",new DateTime(),metaObject);
    }
}
