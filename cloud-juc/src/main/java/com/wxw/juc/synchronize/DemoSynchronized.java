package com.wxw.juc.synchronize;

import java.util.concurrent.CountDownLatch;

/**
 * @author weixiaowei
 * @desc:
 * @link：https://joonwhee.blog.csdn.net/article/details/117401299
 * @date: 2021/7/15
 */
public class DemoSynchronized {
    // public static volatile int race = 0;
    public static int race = 0;

    private static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        // 循环开启2个线程来计数
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                // 每个线程累加1万次
                for (int j = 0; j < 10000; j++) {
                    synchronized (DemoSynchronized.class) {
                        race++;
                    }
                }
                countDownLatch.countDown();
            }).start();
        }
        // 等待，直到所有线程处理结束才放行
        countDownLatch.await();
        // 期望输出 2万（2*1万）
        System.out.println(race);
    }

}
