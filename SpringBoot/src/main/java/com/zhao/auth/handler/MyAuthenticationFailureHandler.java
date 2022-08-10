package com.zhao.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                mapper.writeValueAsString(
                        Result.error(ResultInfo.CODE_600,"账号或密码错误")
                )
        );
    }
}
