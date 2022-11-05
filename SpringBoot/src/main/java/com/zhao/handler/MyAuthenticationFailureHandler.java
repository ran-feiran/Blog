package com.zhao.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zhao.constant.CommonConst.CONTENT_TYPE;
import static com.zhao.enums.StatusCodeEnum.USERNAME_PASSWORD_ERROR;
import static com.zhao.result.Result.error;


@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(
                mapper.writeValueAsString(
                        error(USERNAME_PASSWORD_ERROR.getCode(),USERNAME_PASSWORD_ERROR.getDesc())
                )
        );
    }
}
