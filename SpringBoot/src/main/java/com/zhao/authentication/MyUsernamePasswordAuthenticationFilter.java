package com.zhao.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhao.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.zhao.enums.StatusCodeEnum.SYSTEM_ERROR;
import static com.zhao.enums.StatusCodeEnum.VALID_ERROR;


/**
 * 用户名密码身份验证过滤器
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Slf4j
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            if (!request.getMethod().equals("POST")) {
                throw new ServiceException(SYSTEM_ERROR.getCode(), SYSTEM_ERROR.getDesc());
            }
            Map<String, String> map;
            ObjectMapper mapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()) {
                map = mapper.readValue(is, Map.class);
            } catch (IOException e) {
                throw new ServiceException(VALID_ERROR.getCode(), VALID_ERROR.getDesc());
            }
            if (Objects.nonNull(map)) {
                String username = map.get("username");
                String password = map.get("password");
                if (username == null){
                    username = "";
                }
                if (password == null){
                    password = "";
                }
                username = username.trim();
                log.info("前台登录信息====用户名：{}，密码：{}",username, password);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authToken);
                return this.getAuthenticationManager().authenticate(authToken);
            }
            return null;
        }
        return null;
    }
}
