package com.xq.learn.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author xiaoqiang
 * @date 2019/6/27 1:00
 */
public class ProxyDemo implements InvocationHandler
{
    private Object target;

    public Object bind(Object object)
    {
        this.target = object;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        System.out.println(method.getName());
        Object result = method.invoke(target, args);
        return result;
    }

    public static void main(String[] args)
    {
        ProxyDemo proxy = new ProxyDemo();
        Animal animal = (Animal) proxy.bind(new Dog());
        animal.run();
        animal.eat();

        List<String> list = new ArrayList<>();
        list.forEach(System.out::print);
        Function<String, String> function = String::toLowerCase;

    }
}
