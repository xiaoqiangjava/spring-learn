package com.xq.learn.plugin.executor;

import java.util.Properties;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * mybatis拦截器
 * @author xiaoqiang
 * @date 2019/11/7 1:33
 */
@Intercepts(@Signature(type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
@Component
@Order(99)
public class PageInterceptor implements Interceptor
{
    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        Executor executor = (Executor) invocation.getTarget();

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties)
    {

    }
}
