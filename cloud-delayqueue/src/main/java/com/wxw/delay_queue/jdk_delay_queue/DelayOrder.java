package com.wxw.delay_queue.jdk_delay_queue;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 11:17 2020/12/14
 * @description：延迟订单
 * @link:
 * @version: v_0.0.1
 */
public class DelayOrder implements Delayed {

    /**
     * 延迟时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private long time;

    /**
     * 订单号
     */
    String orderNumber = null;

    // 构造函数计算好延迟后的时间
    public DelayOrder(String orderNumber, long time, TimeUnit unit) {
        this.orderNumber = orderNumber;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
    }

    /**
     * 返回距离你自定义的超时时间还有多少
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    /**
     * https://www.runoob.com/java/java-string-compareto.html
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        DelayOrder Order = (DelayOrder) o;
        long diff = this.time - Order.time;
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

//    void print() {
//        System.out.println(orderNumber + "编号的订单准备删除，删除时间："
//                + DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()));
//    }

}
