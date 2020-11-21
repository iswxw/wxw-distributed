package com.wxw.juc.juc_volatile;

import java.util.concurrent.CountDownLatch;

/**
 * 并发线程计数器
 *
 * @author: wxw
 * @date: 2020-11-21-18:00
 */
public class AtomicDemo {

    private static int count;

    public static void main(String[] args) {
        int threads = 6;
        // 倒计数锁存器 并发协同用
        CountDownLatch downLatch = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i1 = 0; i1 < 10000; i1++) {
                        AtomicDemo.increment();
                    }
                    downLatch.countDown();
                }
            }).start();
        }

        // 一直等待，直到countdown 递减为0
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(AtomicDemo.count);
        // 请使用两个线程依次打印1A2B3C 一个线程打印数字，一个线程打印字母
    }

    private static void increment() {
        count++;
    }
}
