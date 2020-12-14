package com.wxw.common.jdk_delay_queue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 11:01 2020/12/14
 * @description： Order1`、`Order2`、`Order3` 下单 分别在 `5秒`、`10秒`、`15秒` 后未支付，则取消订单
 * @link:
 * @version: v_0.0.1
 */
public class DelayQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        DelayOrder orderNumber1 = new DelayOrder("orderNumber1", 5, TimeUnit.SECONDS);
        DelayOrder orderNumber2 = new DelayOrder("orderNumber2", 10, TimeUnit.SECONDS);
        DelayOrder orderNumber3 = new DelayOrder("orderNumber3", 15, TimeUnit.SECONDS);
        DelayQueue<DelayOrder> delayOrders = new DelayQueue<>();
        delayOrders.put(orderNumber1);
        delayOrders.put(orderNumber2);
        delayOrders.put(orderNumber3);
        System.out.println("订单延迟队列开始时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        while (delayOrders.size() != 0){
            /**
             * 取队列头部元素是否过去
             * poll()：获取并移除队列的超时元素，没有则返回空
             * take()：获取并移除队列的超时元素，如果没有则wait当前线程，直到有元素满足超时条件，返回结果。
             */
//             DelayOrder task = delayOrders.poll();
            DelayOrder task1 = delayOrders.take();
            if (task1 != null) {
                System.out.format("订单:{%s}被取消, 取消时间:{%s}\n", task1.orderNumber, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
//            Thread.sleep(1000);
        }
    }
}
