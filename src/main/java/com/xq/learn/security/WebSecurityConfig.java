package com.xq.learn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring security整合:
 * 整合需要提供两个实现类：UserDetails和UserDetailsService, UserDetails提供用户信息，UserDetailsService提供获取用户信息的方式，
 * 一般都是重写方法，然后从数据库中获取用户信息。
 * spring security 默认使用的是UsernamePasswordAuthenticationFilter进行鉴权，attemptAuthentication()方法用于鉴权，
 * 鉴权成功之后会调用successfulAuthentication方法
 * 该案例使用JWT实现无状态认证，不使用session。
 * 实现UsernamePasswordAuthenticationFilter, 等鉴权成功将JWT生成的token放到响应头中
 * @author xiaoqiang
 * @date 2020/4/5 21:00
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationSuccess jwtAuthenticationSuccess;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // spring提供了三种存储用户的方式，1. 基于内存；2.基于数据库存储；3.实现UserDetailsService来提供用户的获取方式（常用）。
        // 这种方式的实现spring security自动帮我们完成了用户名和密码的验证，如果密码在数据库中需要加密，spring要求加密存储，
        // 可以使用PasswordEncoder,也可以实现下面的AuthenticationProvider自己完成鉴权的逻辑，密码加解密可以使用自己
        // 系统统一的加解密方式。
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        // 自定义AuthenticationProvider身份验证组件, 该provider提供了已经认证的用户信息，spring security判断
        // 用户有没有权限就是通过Authentication对象中的isAuthenticated()方法的返回值来判断的，因此这里整合JWT时我们自定义来
        // 实现用户名和密码的判断
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable().authorizeRequests()
                // login请求放行
                .antMatchers("/login", "/error").permitAll()
                // 其他所有的请求都需要鉴权
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/login")
                .passwordParameter("password").usernameParameter("username")
                .successHandler(jwtAuthenticationSuccess);
        // 在该校验之前加入jwt filter，如果jwt校验通过，则不会走鉴权流程
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String admin = encoder.encode("admin");
        System.out.println(admin);
    }
}
