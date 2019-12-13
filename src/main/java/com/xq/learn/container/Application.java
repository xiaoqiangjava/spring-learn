package com.xq.learn.container;

import java.util.concurrent.ThreadPoolExecutor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@ComponentScan("com.xq.learn")
@MapperScan(value = {"com.xq.learn.dao.movie"})
public class Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    @EnableAsync
    @Configuration
    static class AsyncConfig
    {
        @Bean
        public ThreadPoolTaskExecutor taskExecutor()
        {
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setThreadNamePrefix("schedule");
            taskExecutor.setThreadGroupName("schedule");
            taskExecutor.setCorePoolSize(20);
            taskExecutor.setMaxPoolSize(50);
            taskExecutor.setKeepAliveSeconds(60);
            taskExecutor.setQueueCapacity(500);
            taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
            taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

            return taskExecutor;
        }
    }

}
