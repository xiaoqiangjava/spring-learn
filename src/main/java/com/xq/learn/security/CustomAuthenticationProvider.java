package com.xq.learn.security;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 自定义身份认证组件，当supports方法返回true时，会进入authenticate进行鉴权.
 * @author xiaoqiang
 * @date 2020/4/5 21:28
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取当前请求的用户名和密码，鉴权
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        // 通过用户名从数据库查询用户信息, 校验密码
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credential.");
        }
        // 从数据库中查询用户拥有的权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        // spring security会默认给role添加ROLE_的前缀，鉴权时需要使用ROLE_ADMIN
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // 构建已经授权的Authentication, 因为已经完成了授权，因此不需要将密码传下去
        // 要构建已经认证了的Authentication，必须在调用三个参数的构造器
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,
                null, authorities);
        logger.info("Succeed to authentication.");
        // UsernamePasswordAuthenticationToken是Authentication的实现类，将已经认证了的返回
        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
