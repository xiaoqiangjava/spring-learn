package com.xq.learn.proxy;

/**
 * @author xiaoqiang
 * @date 2019/6/27 1:01
 */
public interface Animal
{
    void run();

    default void eat()
    {

    }
}
