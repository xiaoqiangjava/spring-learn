package com.xq.learn.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 任务调度类，执行具体的定时任务
 * @author xiaoqiang
 * @date 2019/12/13 23:31
 */
@Component
public class ScheduleTask
{
    private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    /**
     * 定时调度
     */
    public void task()
    {
        logger.info("[{}]task: Quartz定时调度测试。。。", Thread.currentThread().getName());
    }

    /**
     * 查询状态
     */
    private void status()
    {
        logger.info("status: Quartz定时任务测试。。。");
    }
}
