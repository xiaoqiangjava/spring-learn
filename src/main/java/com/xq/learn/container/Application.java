package com.xq.learn.container;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.xq.learn")
@MapperScan(value = {"com.xq.learn.dao.movie"})
public class Application
{

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

}
