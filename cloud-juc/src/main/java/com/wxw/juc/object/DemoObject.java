package com.wxw.juc.object;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/15
 */
public class DemoObject {
    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();

        // 需要操作锁对象，所以需要先获取锁
        obj.notify();
        obj.notifyAll();
        obj.wait();
    }
}
