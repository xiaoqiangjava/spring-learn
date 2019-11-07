package com.xq.learn.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 从spring容器中获取bean实例工具类
 * @author xiaoqiang
 * @date 2019/11/7 0:59
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * 从spring容器中获取bean实例
     * @param name
     * @param <T>
     * @return
     */
    @SuppressWarnings("all")
    public static <T> T getBean(String name)
    {
        return (T) applicationContext.getBean(name);
    }
}
