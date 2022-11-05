package com.zhao.annotations;


import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author ran-feiran
 * @date 2022/10/17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * 选择类型
     *
     * @return 操作类型
     */
    String optType() default "";
}

