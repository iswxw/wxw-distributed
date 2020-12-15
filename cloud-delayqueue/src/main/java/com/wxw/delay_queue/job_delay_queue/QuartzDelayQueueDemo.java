package com.wxw.delay_queue.job_delay_queue;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author ：wxw.
 * @date ： 10:40 2020/12/14
 * @description：基于quartz的延迟队列
 * @link:
 * @version: v_0.0.1
 */
public class QuartzDelayQueueDemo implements Job {

    // 下单时间
    Date submitOrder = null;

    // 取消订单时间：下单30分钟后 检测如果未支付，则自动取消订单，生成订单60s后，给客户发短信
    Date executeTime = null;

    public static void main(String[] args) throws SchedulerException {
        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(QuartzDelayQueueDemo.class)
                .withIdentity("job1", "group1").build();
        // 创建触发器 每3秒钟执行一次
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1", "group3")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(3).repeatForever())
                .build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        // 将任务及其触发器放入调度器
        scheduler.scheduleJob(jobDetail, trigger);
        // 调度器开始调度任务
        scheduler.start();

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("我是定时任务！\n 我来看一下延迟时间够了没有！！");
        /**
         * 检测当前时间 是否是取消订单时间
         * 当前时间 - 执行时间 如果误差小于 2s则取消订单
         */
        if (System.currentTimeMillis() - executeTime.getTime() < 2000) {
            System.out.println("取消订单逻辑处理");
            /**
             * 取消订单逻辑
             * 此处省略1000万行代码
             */
            System.out.println("发送短信提醒客户，订单已取消");
        }
    }
}
