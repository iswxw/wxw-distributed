package com.wxw.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * https://www.cnblogs.com/2019wxw/p/11663067.html
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有人都到齐了吧，咱们开会。。。");
            }
        });
        for (int i = 1; i <= 4; i++) {
            final int tempInt = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "\t" + tempInt + "已到位");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}