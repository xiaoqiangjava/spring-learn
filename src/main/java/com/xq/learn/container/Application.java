package com.xq.learn.container;

import io.swagger.annotations.Api;
import java.util.concurrent.ThreadPoolExecutor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.xq.learn")
@MapperScan(value = {"com.xq.learn.dao.movie"})
@EnableSwagger2
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

    @Bean
    public Docket createRestApi(ApiInfo apiInfo)
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("api文档")
                .description("swagger2自动生成api文档")
                .version("v1")
                .build();
    }

}
