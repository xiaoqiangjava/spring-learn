package com.xq.learn.proxy;

/**
 * @author xiaoqiang
 * @date 2019/6/27 1:01
 */
public class Dog implements Animal
{
    @Override
    public void run()
    {
        System.out.println("Dog is running");
    }

    @Override
    public void eat()
    {
        System.out.println("Dog is eating");
    }
}
