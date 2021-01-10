package com.wxw.delay_queue.timer_delay_queue;

import com.wxw.common.manager.tools.DateTools;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ：wxw.
 * @date ： 13:54 2020/12/14
 * @description：定时器
 * @link:
 * @version: v_0.0.1
 */
public class TimerDemo {
    public static void main(String[] args) {
        Timer timer = new Timer();// 实例化Timer类
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("退出，时间："+ DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()));
                this.cancel();
            }
        }, 5000); // 5s
        System.out.println("本程序存在5秒后自动退出,时间："+ DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()));

    }
}
