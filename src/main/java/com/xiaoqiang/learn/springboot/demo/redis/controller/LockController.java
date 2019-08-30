package com.xiaoqiang.learn.springboot.demo.redis.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用redis实现分布式锁
 * @author xiaoqiang
 * @date 2019/8/31 0:46
 */
@RestController
@RequestMapping("/v1/lock")
public class LockController
{
    private static final Logger logger = LoggerFactory.getLogger(LockController.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redisson;
    /**
     * 使用Redis实现分布式锁
     * @return
     */
    @RequestMapping(value = "/redis/product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String distributeLock()
    {
        // 为每个线程创建一个唯一标识，释放锁时确保释放的是自己的锁
        String requestId = UUID.randomUUID().toString();
        Boolean flag = redisTemplate.opsForValue().setIfAbsent("product_001_lock", requestId, 10, TimeUnit.SECONDS);
        if (!flag)
        {
            return "Please try later.";
        }
        try
        {
            // 查询商品库存，如果库存大于0，对库存减一
            String productStock = redisTemplate.opsForValue().get("product_001");
            if (null != productStock && Integer.valueOf(productStock) > 0)
            {
                redisTemplate.opsForValue().set("product_001", (Integer.valueOf(productStock) -1) + "");
                logger.info("Stock: " + productStock);
            }
            else
            {
                logger.info("out of stock.");
                return "out of stock.";
            }
        }
        finally
        {
            // 程序执行完成之后删除掉已经存在的key，释放锁
            if (requestId.equals(redisTemplate.opsForValue().get("product_001_lock")))
            {
                logger.info("unlock");
                redisTemplate.delete("product_001_lock");
            }
        }
        return "success";
    }

    /**
     * 使用Redisson实现分布式锁
     * @return
     */
    @RequestMapping(value = "/redisson/product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String redissonLock()
    {
        RLock rLock = redisson.getLock("product_001_lock");
        try
        {
            rLock.lock();
            // 查询商品库存，如果库存大于0，对库存减一
            logger.info("do something.");
        }
        finally
        {
            // 程序执行完成之后删除掉已经存在的key，释放锁
            rLock.unlock();
        }
        return "success";
    }
}
