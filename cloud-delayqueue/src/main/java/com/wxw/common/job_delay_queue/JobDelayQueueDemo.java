package com.wxw.common.job_delay_queue;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ：wxw.
 * @date ： 17:10 2020/12/9
 * @description：基于定时任务的延迟队列
 * @link:
 * @version: v_0.0.1
 */
@Component
public class JobDelayQueueDemo {

    /**
     * 每隔五秒
     */
    @Scheduled(cron = "0/5 * * * * ? ")
    public void process(){

        /**
         * 下单时间，如果当前时间超过下单时间30分钟未支付 则发送短信提醒将自动取消订单
         */
        String submitOrder = "2020-07-22 13:00:00";

        System.out.println("我是定时任务！\n 我来看一下延迟时间够了没有！！" );
    }
}
