package com.wxw.juc;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore是一个计数信号量，常用于限制可以访问某些资源（物理或逻辑的）线程数目。
 *
 * 假设有10个人在银行办理业务，只有2个工作窗口，代码实现逻辑如下
 */
public class SemaphoreDemo {

    // 排队总人数（请求总数）
    public static int clientTotal = 10;

    // 可同时受理业务的窗口数量（同时并发执行的线程数）
    public static int threadTotal = 2;


    public static void main(String[] args) throws Exception {

        // 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(threadTotal);

        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire(1);
                    // 获取许可后进行相关逻辑处理
                    resolve(count);
                    semaphore.release(1); // 释放许可，供其他线程使用
                } catch (Exception e) {
                    System.out.println("exception"+ e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("countDownLatch = " + "好了，票终于卖完了");
        executorService.shutdown();
    }


    private static void resolve(int i) throws InterruptedException {
        System.out.println("服务号："+i+"，受理业务中————!");
        Thread.sleep(2000);
    }
}