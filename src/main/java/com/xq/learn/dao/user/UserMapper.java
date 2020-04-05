package com.xq.learn.dao.user;

import com.xq.learn.security.UserEntity;

/**
 * 用户相关Mapper接口
 * @author xiaoqiang
 * @date 2019/11/6 23:01
 */
public interface UserMapper
{
    UserEntity queryByName(String username);
}
