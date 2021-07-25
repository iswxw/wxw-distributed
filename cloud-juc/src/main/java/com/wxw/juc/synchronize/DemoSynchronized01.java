package com.wxw.juc.synchronize;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/19
 */
public class DemoSynchronized01 {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // 锁作用于代码块
        synchronized (lock) {
            System.out.println("hello word");
        }
    }

    // 锁作用于方法
    public synchronized void test() {
        System.out.println("test");
    }
}
