package com.sky.springtask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ⾃定义定时任务类
 */
@Component
@Slf4j
public class MyTask {
    /**
     * 定时任务 每隔50秒触发⼀次
     */
    @Scheduled(cron = "0/50 * * * * ?")
    public void executeTask(){
        log.info("定时任务开始执⾏：{}",new Date());
    }
}