package com.wxw.juc;

import java.util.concurrent.CountDownLatch;

/**
 * https://www.cnblogs.com/skywang12345/p/java_threads_category.html
 * @ Author ：wxw.
 * @ Date ： 17:14 2020/9/21
 * @ Description：CountDownLatch
 * @ Version:   v_0.0.1
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchUsed();
    }

    public static void CountDownLatchUsed() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 下自习走人");
                downLatch.countDown();
            }, String.valueOf(i)).start();
        }
        downLatch.await();
        System.out.println(Thread.currentThread().getName() + "自习室关门走人");
    }
}
