package com.wanjun.mail.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * spring-boot 自带的定时任务
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTask {

    private static Logger log = LoggerFactory.getLogger(Scheduled.class);

    /**
     * @Scheduled 注解中的一些属性
     * fixedRate 固定频率
     * fixedDelay 固定间隔
     * cron 表达式
     * 下面介绍的是固定频率的注解,单位为ms
     */
    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        log.info("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date()));
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void reportCurrentTimeCron(){
        log.info("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date()));
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }
}
