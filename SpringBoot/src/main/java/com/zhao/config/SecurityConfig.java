package com.zhao.config;


import com.zhao.auth.MyUserDetailsService;
import com.zhao.auth.MyUsernamePasswordAuthenticationFilter;
import com.zhao.auth.handler.*;
import com.zhao.authority.MyAccessDecisionManager;
import com.zhao.authority.MyFilterInvocationSecurityMetadatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private MyFilterInvocationSecurityMetadatasource myFilterInvocationSecurityMetadatasource;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                formLogin().
                loginProcessingUrl("/login");// 登录请求的url
        http.
                authorizeRequests().
                requestMatchers(CorsUtils::isPreFlightRequest).
                permitAll();
        //放行掉这个iframe加载
        http.
                headers().
                frameOptions().
                disable();
        http.
                logout().
                logoutUrl("/logout").
                logoutSuccessHandler(myLogoutSuccessHandler);
        http
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                        fsi.setSecurityMetadataSource(myFilterInvocationSecurityMetadatasource);
                        fsi.setAccessDecisionManager(myAccessDecisionManager);
                        return fsi;
                    }
                });
//                .anyRequest()
//                .access(("@dynamicPermission.checkPermission(authentication)"));
        http.
                csrf().
                disable().
                exceptionHandling();
        http.
                addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.
                exceptionHandling().
                accessDeniedHandler(myAccessDeniedHandler).  // 访问接口没有权限的处理
                authenticationEntryPoint(myAuthenticationEntryPoint);  // 没有登录的异常处理
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();
        myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        myUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        return myUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
