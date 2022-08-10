package com.zhao.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
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



public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        //携带头要是json格式得application/json;charset=UTF-8
        //前后端分离 这里是接收的是json串
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            if (!request.getMethod().equals("POST")) {
                throw new AuthenticationServiceException(
                        "Authentication method not supported: " + request.getMethod());
            }

            Map<String, String> map = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()) {
                //字符串转集合
                map = objectMapper.readValue(is, Map.class);
            } catch (IOException e) {
                //将异常放到自定义的异常类中
                e.printStackTrace();
            }
//            System.out.println(map);
            if (map != null) {
                String username = map.get("username");
                String password = map.get("password");
//                System.out.println(username);
//                System.out.println(password);
                if (username == null){
                    username = "";
                }
                if (password == null){
                    password = "";
                }
                username = username.trim();
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                // Allow subclasses to set the "details" property
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
            return null;
        }
        return null;
    }
}
