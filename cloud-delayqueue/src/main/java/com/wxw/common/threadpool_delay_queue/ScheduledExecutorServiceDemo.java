package com.wxw.common.threadpool_delay_queue;

import com.wxw.common.tools.DateTools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 13:36 2020/12/14
 * @description：基于线程池实现延迟队列
 * @link:
 * @version: v_0.0.1
 */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorServiceDemo serviceDemo = new ScheduledExecutorServiceDemo();
        serviceDemo.ScheduledExecutorServiceTest();
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(100);
//        /**
//         * 延迟秒数递减
//         */
//        for (int i = 10; i > 0; i--) {
//            executor.schedule(() -> System.out.println("Work start, thread id:" + Thread.currentThread().getId() + " "
//                    + DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis())), i, TimeUnit.SECONDS);
//        }

    }

    public void ScheduledExecutorServiceTest() throws InterruptedException {

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        System.out.println("创建5秒延迟的任务,时间："+ DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()));
        ScheduledFuture<?> schedule = scheduledExecutorService.schedule(() -> doTask("5s"), 5, TimeUnit.SECONDS);

        Thread.sleep(4900);

        schedule.cancel(false);
        System.err.println("取消5秒延迟的任务，时间：" + DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()));

        System.out.println("创建3秒延迟的任务，时间" + DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()));
        ScheduledFuture<?> schedule2 = scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                doTask("3s");
            }
        }, 3, TimeUnit.SECONDS);

        Thread.sleep(4000);

    }

    private void doTask(String arg) {
        System.out.println(arg + " 任务执行，时间： " + DateTools.getLong2YyyyMmDdHhMmSs(System.currentTimeMillis()));
    }
}
