package com.xq.learn.plugin.statement;

import java.sql.Statement;
import java.util.Properties;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * mybatis拦截器
 * @author xiaoqiang
 * @date 2019/11/7 2:04
 */
@Intercepts(@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}))
@Component
public class StatementInterceptor implements Interceptor
{
    private static final Logger logger = LoggerFactory.getLogger(StatementInterceptor.class);

    private static final String WRITE_PATTERN = "\\s+|\n|\t";

    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        long start = System.currentTimeMillis();
        try
        {
            return invocation.proceed();
        }
        finally
        {
            logger.info("sql cost time: {} ms", System.currentTimeMillis() - start);
        }
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
