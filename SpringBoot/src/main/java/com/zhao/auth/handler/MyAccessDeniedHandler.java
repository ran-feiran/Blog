package com.zhao.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.result.Result;
import com.zhao.result.ResultInfo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拒绝访问处理程序的处理
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(
                mapper.writeValueAsString(Result.error(ResultInfo.CODE_401,"权限不足2333,请找站长升级权限吧,宝~"))
        );
    }
}
