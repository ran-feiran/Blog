package com.zhao.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zhao.constant.CommonConst.CONTENT_TYPE;
import static com.zhao.enums.StatusCodeEnum.AUTHORIZED;
import static com.zhao.result.Result.error;

/**
 * 拒绝访问处理者
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(
                mapper.writeValueAsString(error(AUTHORIZED.getCode(),AUTHORIZED.getDesc()))
        );
    }
}
