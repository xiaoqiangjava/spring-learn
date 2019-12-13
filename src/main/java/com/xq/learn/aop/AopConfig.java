package com.xq.learn.aop;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置方法过滤器：DefaultPointcutAdvisor是一个非常强大的Advisor，可以自定义Advice和Pointcut
 * @author xiaoqiang
 * @date 2019/11/9 1:52
 */
@Configuration
public class AopConfig
{
    private static final String POINT_CUT = "execution(* com.xq.learn.service.impl.movie.*.*(..))";

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor()
    {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        // 设置切入点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(POINT_CUT);
        // 创建interceptor实例
        LogInterceptor advice = new LogInterceptor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(advice);

        return advisor;
    }
}
