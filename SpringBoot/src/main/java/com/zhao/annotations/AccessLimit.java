package com.zhao.annotations;


import java.lang.annotation.*;

/**
 * 接口限流注解
 *
 * @author ran-feiran
 * @date 2022/10/29
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 单位时间（秒）
     *
     * @return int
     */
    int seconds();

    /**
     * 单位时间最大请求次数
     *
     * @return int
     */
    int maxCount();

    /**
     *
     * 描述
     *
     * @return {@link String}
     */
    String desc() default "";
}
