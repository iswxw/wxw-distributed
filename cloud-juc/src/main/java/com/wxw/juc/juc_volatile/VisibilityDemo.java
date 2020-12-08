package com.wxw.juc.juc_volatile;

import java.util.concurrent.TimeUnit;

/**
 * 内存可见性
 * @author: wxw
 * @date: 2020-11-21-7:53
 */
public class VisibilityDemo {
    // 状态标识
//    private static  boolean is = true;
    private static volatile boolean is = true;
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (VisibilityDemo.is) {
                    synchronized (this) { i++; }  // i = 67221967
//                i++;
                }
                // 问题1：线程会停止循环打印i吗
                System.out.println("i = " + i);
            }
        }, "加加操作").start();
        // 等2秒
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
        VisibilityDemo.is = false; // 设置is为false,使上面while停止循环
        System.out.println("is被设置为false了.");
    }
}
