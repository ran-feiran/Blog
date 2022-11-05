package com.zhao.handler;

import com.alibaba.fastjson2.JSON;
import com.zhao.annotations.AccessLimit;
import com.zhao.api.RedisService;
import com.zhao.result.ResultStandby;
import com.zhao.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static com.zhao.constant.CommonConst.CONTENT_TYPE;
import static com.zhao.enums.StatusCodeEnum.FAIL;


@Slf4j
public class WebSecurityHandler implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("====================SpringSecurity执行后====================");
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            // 获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit != null) {
                int seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                // 针对方法和ip进行限流
                String key = IpUtil.getIp(request) + ":" + hm.getMethod().getName();
                // 从redis中获取用户访问的次数
                try {
                    // 此操作代表获取该key对应的值自增1后的结果
                    long count = redisService.incrExpire(key, seconds);
                    if (count > maxCount) {
                        render(response, ResultStandby.error(FAIL.getCode(), accessLimit.desc()));
                        log.warn(key + "请求次数超过每" + seconds + "秒" + maxCount + "次");
                        return false;
                    }
                    return true;
                } catch (RedisConnectionFailureException e) {
                    log.warn("redis错误: {}", e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }



    private void render(HttpServletResponse response, ResultStandby<?> result) throws Exception {
        response.setContentType(CONTENT_TYPE);
        OutputStream out = response.getOutputStream();
        out.write(JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
