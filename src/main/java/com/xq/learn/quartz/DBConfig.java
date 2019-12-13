package com.xq.learn.quartz;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author xiaoqiang
 * @date 2019/12/14 1:43
 */
@Configuration
public class DBConfig
{
    @Bean
    @Primary
    public DataSource dataSource()
    {
        return DataSourceBuilder.create().driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://127.0.0.1:3306/recommend?useUnicode=true&characterEncoding=utf8")
                .username("root")
                .password("root")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @QuartzDataSource
    public DataSource quartzDataSource()
    {
        return DataSourceBuilder.create().driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://127.0.0.1:3306/quartz?useUnicode=true&characterEncoding=utf8")
                .username("root")
                .password("root")
                .build();
    }

    @Bean
    public PlatformTransactionManager quartzTransactionManager(@Qualifier("quartzDataSource") DataSource dataSource)
    {
        return new DataSourceTransactionManager(dataSource);
    }


}
