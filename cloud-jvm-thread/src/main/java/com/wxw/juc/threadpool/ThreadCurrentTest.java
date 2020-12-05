package com.wxw.juc.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * -xmx100M
 * visualvm
 * @author: wxw
 * @date: 2020-12-05-22:03
 */
public class ThreadCurrentTest {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5000);
        try {
            TimeUnit.SECONDS.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("i = " + i);
        }
    }
}
