package com.zhao.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.zhao.constant.CommonConst.CONTENT_TYPE;
import static com.zhao.result.Result.success;

/**
 * 注销成功处理者
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(
                mapper.writeValueAsString(success()));
    }
}
