package com.xq.learn.security;

import com.xq.learn.dao.user.UserMapper;
import javax.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 实现UserDetailsService接口，供spring security获取用户数据。
 * @author xiaoqiang
 * @date 2020/4/5 21:07
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 该方法封装了获取用户的方式，spring security通过调用该方法获取到用户实例
        return userMapper.queryByName(username);
    }
}
