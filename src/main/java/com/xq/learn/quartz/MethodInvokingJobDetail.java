package com.xq.learn.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 调度任务类，实现具体的任务调度
 * QuartzJobBean是spring对quartz的支持，该类实现了{@link org.quartz.Job}接口, 只需要实现这个类中的abstract接口
 * 就可以实现作业的调度。
 * 一般使用时结合spring，会在该类中注入{@link ApplicationContext}，然后通applicationContext来获取{@link org.quartz.JobDataMap}
 * 中指定的targetObject的bean对象，然后调用targetMethod指定的方法实现任务的调度，这样写的好处是不用每一个作业都实现{@link org.quartz.Job}
 * 接口，而是配置多个bean，然后通过反射来调用。
 * @author xiaoqiang
 * @date 2019/12/13 22:57
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MethodInvokingJobDetail extends QuartzJobBean
{
    private String targetObject;

    private String targetMethod;

    private ApplicationContext applicationContext;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        Object target = applicationContext.getBean(targetObject);
        Method method = null;
        try
        {
            method = target.getClass().getMethod(targetMethod);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        try
        {
            method.setAccessible(true);
            method.invoke(target);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

    }

    public String getTargetObject()
    {
        return targetObject;
    }

    public void setTargetObject(String targetObject)
    {
        this.targetObject = targetObject;
    }

    public String getTargetMethod()
    {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod)
    {
        this.targetMethod = targetMethod;
    }

    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }
}
