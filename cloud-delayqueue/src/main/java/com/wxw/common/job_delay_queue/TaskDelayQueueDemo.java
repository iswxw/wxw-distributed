package com.wxw.common.job_delay_queue;

import cn.hutool.core.date.DateUtil;
import com.wxw.common.tools.DateTools;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：wxw.
 * @date ： 17:10 2020/12/9
 * @description：基于定时任务的延迟队列
 * @link:
 * @version: v_0.0.1
 */
@Component
public class TaskDelayQueueDemo {

    // 下单时间
    Date submitOrder = new Date();

    // 取消订单时间：下单30分钟后 检测如果未支付，则自动取消订单，生成订单60s后，给客户发短信
    Date executeTime = DateUtils.addSeconds(submitOrder,30); // 30 分钟以后的时间

    /**
     * Step2: 定时扫描数据库
     *
     * 每隔五秒
     */
    // @Scheduled(cron = "0/5 * * * * ? ")
    public void process(){

        System.out.println("我是定时任务！\n 我来看一下延迟时间够了没有！！" );
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

    /**
     * Step1: 提交订单，存入数据库
     */
    public void submitOrder(){
        /**
         * 下单时间，如果当前时间超过下单时间30分钟未支付 则发送短信提醒将自动取消订单
         */
        submitOrder = DateTools.string2DateTime("2020-07-22 13:00:00");

        /**
         *  取消订单时间：下单30分钟后 检测如果未支付，则自动取消订单，生成订单60s后，给客户发短信
         */
        executeTime = DateUtils.addMinutes(submitOrder,30);

        /**
         * 存入数据库
         * 此处省略 100000万行代码
         */
    }
}
