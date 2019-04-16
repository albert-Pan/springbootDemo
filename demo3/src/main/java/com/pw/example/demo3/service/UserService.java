package com.pw.example.demo3.service;

import com.pw.example.demo3.times.UserTestTimer;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    /**
     * 注入任务调度器
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * 构建创建商品定时任务
     */
    public void buildTimer() throws Exception {
        //任务名称
        String name = UUID.randomUUID().toString();
        //任务所属分组
        String group = UserTestTimer.class.getName();

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(UserTestTimer.class).withIdentity(name, group).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(scheduleBuilder).build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
