package com.wxw.juc.threadpool;

import java.util.concurrent.ForkJoinPool;

/**
 * @author: wxw
 * @date: 2021-01-24-10:38
 * @link:
 * @description:
 */
public class ForkJoinPollDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("forkJoinPool = " + forkJoinPool);
            }
        });
    }
}
