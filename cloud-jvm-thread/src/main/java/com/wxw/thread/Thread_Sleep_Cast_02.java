package com.wxw.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ Author ：wxw.
 * @ Date ： 20:04 2020/7/4
 * @ Description：线程在睡眠时，拥有的监视器资源不会被释放
 * @ Version:   v_0.0.1
 */
public class Thread_Sleep_Cast_02 {

    // 创建一个独占锁
    public static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("子线程A 睡眠");
                    Thread.sleep(1000);
                    System.out.println("子线程A 被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });

        // 启动线程
        A.start();

        // 在睡眠时 主线程试图中断线程A,会抛出不可中断异常
        A.interrupt();
        
    }
}
