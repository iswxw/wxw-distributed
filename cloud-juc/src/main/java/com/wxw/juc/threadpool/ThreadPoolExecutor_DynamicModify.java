package com.wxw.juc.threadpool;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: wxw
 * @date: 2021-01-10-11:17
 * @link:
 * @description: 线程池 动态更新核心线程数
 */
public class ThreadPoolExecutor_DynamicModify {
    public static void main(String[] args) throws InterruptedException {
        dynamicModifyExecutor();
    }

    // 自定义线程池
    private static ThreadPoolExecutor buildThreadPoolExecutor() {
        return new ThreadPoolExecutor(2,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new NamedThreadFactory("Java半颗糖"));
    }

    /**
     * 先提交任务给线程池，并修改线程池参数
     *
     * @throws InterruptedException
     */
    private static void dynamicModifyExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = buildThreadPoolExecutor();
        for (int i = 0; i < 15; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolStatus(executor, "改变之前");
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        threadPoolStatus(executor, "改变之后");
        Thread.currentThread().join();
    }

    /**
     * 打印线程池状态
     *
     * @param executor
     * @param name
     */
    private static void threadPoolStatus(ThreadPoolExecutor executor, String name) {
        LinkedBlockingQueue queue = (LinkedBlockingQueue) executor.getQueue();
        System.out.println(Thread.currentThread().getName() + "-" + name + "-:" +
                "核心线程数：" + executor.getCorePoolSize() +
                "活动线程数：" + executor.getActiveCount() +
                "最大线程数：" + executor.getMaximumPoolSize() +
                "线程池活跃度：" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
                "任务完成数：" + executor.getCompletedTaskCount() +
                "队列大小：" + (queue.size() + queue.remainingCapacity()) +
                "当前队列线程数：" + queue.size() +
                "队列剩余大小：" + queue.remainingCapacity() +
                "队列使用度：" + divide(queue.size(), queue.size() + queue.remainingCapacity()));
    }

    /**
     * 保留两位小数
     *
     * @param num1
     * @param num2
     * @return
     */
    private static String divide(int num1, int num2) {
        return String.format("%1.2f%%",
                Double.parseDouble(num1 + "") / Double.parseDouble(num2 + "") * 100);
    }


    private static String getTime() {
        LocalTime time = LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

}
