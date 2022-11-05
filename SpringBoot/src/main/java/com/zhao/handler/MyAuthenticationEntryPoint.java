package com.zhao.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zhao.constant.CommonConst.CONTENT_TYPE;
import static com.zhao.enums.StatusCodeEnum.NO_LOGIN;
import static com.zhao.result.Result.error;

/**
 *
 * 身份验证入口点
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(
                mapper.writeValueAsString(error(NO_LOGIN.getCode(),NO_LOGIN.getDesc()))
        );
    }
}
