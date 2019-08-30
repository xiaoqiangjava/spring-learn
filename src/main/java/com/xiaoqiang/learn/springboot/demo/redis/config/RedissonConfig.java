package com.xiaoqiang.learn.springboot.demo.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Redisson
 * @author xiaoqiang
 * @date 2019/8/31 2:37
 */
@Configuration
public class RedissonConfig
{
    @Bean
    public RedissonClient redisson()
    {
        Config config = new Config();
        config.setLockWatchdogTimeout(10);
        config.useSingleServer().setAddress("redis://learn:6379").setPassword("xiaoqiang");
        return Redisson.create(config);
    }
}
