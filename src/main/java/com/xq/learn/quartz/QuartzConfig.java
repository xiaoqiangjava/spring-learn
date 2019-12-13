package com.xq.learn.quartz;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * springboot整合quartz：使用集群形式部署
 * 配置的入口：{@link org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration}
 * @see SchedulerFactoryBeanCustomizer
 * 使用SchedulerFactoryBeanCustomizer实现 {@link org.springframework.scheduling.quartz.SchedulerFactoryBean}
 * 的自定义。
 * @author xiaoqiang
 * @date 2019/12/13 22:37
 */
@Configuration
public class QuartzConfig
{
    @Autowired
    private ScheduleTask scheduleTask;

    @Autowired
    private ThreadPoolTaskExecutor taskExcutor;

    /**
     * 自定义SchedulerFactoryBean，设置ApplicationContext属性以及PlatformTransactionManager，特别适用
     * 于业务数据库和quartz数据库分离不是同一个数据源的情况下设置事物管理器，Order控制调用的顺序在框架调用DataSource
     * 之后，可以使用@QuartzDataSource来指定quartz使用的数据源。当系统中存在多个数据源时，需要使用@Primary注解指定
     * 主数据源。
     * @param txManager 事物管理器
     * @return SchedulerFactoryBean
     */
    @Bean
    @Order(1)
    public SchedulerFactoryBeanCustomizer schedulerCustomizer(@Qualifier("quartzTransactionManager") PlatformTransactionManager txManager)
    {
        return schedulerFactoryBean ->
        {
            // 将ApplicationContext的值设置到SchedulerContext中，可以在JobDetailFactoryBean中获取IOC中的Bean
            schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
            schedulerFactoryBean.setTransactionManager(txManager);
            schedulerFactoryBean.setTaskExecutor(taskExcutor);
        };
    }

    /**
     * 触发器，在触发器中设置{@link org.quartz.JobDetail}对象和{@link org.quartz.CronExpression}
     * @param jobDetail jobDetail
     * @return CronTriggerFactoryBean#getObject()来获取trigger实例
     */
    @Bean
    public CronTriggerFactoryBean statusTrigger(JobDetailFactoryBean jobDetail)
    {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail.getObject());
        trigger.setCronExpression("0/15 * * * * ? *");
        trigger.setGroup("learn");
        return trigger;
    }

    /**
     * 使用该种JobDetailFactoryBean创建的jobDetail，需要指定jobClass，然后通过applicationContext获取调用的bean，
     * 再通过反射来调用具体的方法。
     * @return JobDetailFactoryBean#getObject() 可以获取JobDetail
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean()
    {
        JobDetailFactoryBean jobDetail = new JobDetailFactoryBean();
        jobDetail.setJobClass(MethodInvokingJobDetail.class);
        jobDetail.setGroup("learn");
        jobDetail.setDurability(true);
        jobDetail.setDescription("This is a learn job detail.");
        Map<String, String> jobData = new HashMap<>();
        jobData.put("targetMethod", "task");
        jobData.put("targetObject", "scheduleTask");
        jobDetail.setJobDataAsMap(jobData);

        return jobDetail;
    }

    /**
     * 这种方式实现了JobDetail的自动调用，不用自己写调用的逻辑代码。
     * 在集群环境下，不能使用 {@link MethodInvokingJobDetailFactoryBean}来创建{@link org.quartz.JobDetail}
     * 因为{@link MethodInvokingJobDetailFactoryBean} 没有办法序列化，所以不能持久化到数据库中
     * @return MethodInvokingJobDetailFactoryBean#getObject() 来获取jobDetail实例
     */
//    @Bean
    public MethodInvokingJobDetailFactoryBean statusJobDetail()
    {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setGroup("learn-status");
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(scheduleTask);
        jobDetail.setTargetMethod("status");

        return jobDetail;
    }

//    @Bean
    public CronTriggerFactoryBean taskTrigger(@Qualifier("taskJobDetail") MethodInvokingJobDetailFactoryBean jobDetail)
    {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail.getObject());
        trigger.setCronExpression("0/15 * * * * ? *");
        trigger.setGroup("learn-task");
        return trigger;
    }

//    @Bean
    public MethodInvokingJobDetailFactoryBean taskJobDetail()
    {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setGroup("learn-task");
        jobDetail.setConcurrent(false);
        jobDetail.setTargetObject(scheduleTask);
        jobDetail.setTargetMethod("task");

        return jobDetail;
    }
}
