package com.xq.learn.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 鉴权，成功之后下发JWT token到响应头中，下次请求需要携带该token，然后在JwtAuthenticationFilter中校验token的有效性
 * @author xiaoqiang
 * @date 2020/4/6 0:47
 */
@Component
public class JwtAuthenticationSuccess implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 下发JWT
        String token = jwtUtil.generateToken(authentication.getName());
        response.setHeader("Authorization", "Bearer " + token);
    }
}
