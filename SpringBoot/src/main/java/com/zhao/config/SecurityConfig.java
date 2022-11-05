package com.zhao.config;

import com.zhao.authentication.MyUserDetailsService;
import com.zhao.authentication.MyUsernamePasswordAuthenticationFilter;
import com.zhao.authority.MyAccessDecisionManager;
import com.zhao.authority.MyFilterInvocationSecurityMetadataSource;
import com.zhao.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * SpringSecurity配置类
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
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
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadatasource;
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
                })
                .anyRequest()
                .permitAll();
        http.
                csrf().
                disable(). // 关闭跨站请求防护
                exceptionHandling().
                accessDeniedHandler(myAccessDeniedHandler).  // 访问接口没有权限的处理
                authenticationEntryPoint(myAuthenticationEntryPoint);  // 没有登录的异常处理
        http.
                addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.
                 sessionManagement().
                 maximumSessions(20).
                 sessionRegistry(sessionRegistry());
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

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
