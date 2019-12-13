package com.xq.learn.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.ReflectiveMethodInvocation;

/**
 * 方法拦截器，spring中实现非controller中的方法拦截，@Transactional, @Async, @Cached等注解都是基于
 * MethodInterceptor实现的。
 * @author xiaoqiang
 * @date 2019/11/9 1:27
 */
@Aspect
public class LogInterceptor implements MethodInterceptor
{
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        Object[] arguments = invocation.getArguments();
        for (int i = 0; i < arguments.length; i++)
        {
            if (arguments[i] instanceof String)
            {
                arguments[i] += ":obj";
            }
        }
        ((ReflectiveMethodInvocation) invocation).setArguments(arguments);
        return invocation.proceed();
    }
}
