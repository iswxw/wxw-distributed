package com.wxw.thread;

import java.util.concurrent.TimeUnit;

/**
 * 死锁现象
 *
 * @Author: wxw
 * @create: 2020-07-18-10:49
 */
public class Thread_Dead_Cast01 {
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread() + "get resourceA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(" 准备获取 resourceB");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + "get resourceB");
                }
            }
        }, "A");

        // 使用顺序执行避免线程死锁
        Thread threadB = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread() + "get resourceA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(" 准备获取 resourceB");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + "get resourceB");
                }
            }
        }, "A");


        // 线程死锁
//        Thread threadB = new Thread(() -> {
//            synchronized (resourceB) {
//                System.out.println(Thread.currentThread() + "get resourceB");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(" 准备获取 resourceA");
//                synchronized (resourceA) {
//                    System.out.println(Thread.currentThread() + "get resourceA");
//                }
//            }
//        }, "B");

        // 启动线程
        threadA.start();
        threadB.start();
    }
}
