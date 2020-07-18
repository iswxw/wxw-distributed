package com.wxw.thread;

/**
 * 守护线程 和用户线程
 * @Author: wxw
 * @create: 2020-07-18-11:08
 */
public class Thread_Daemon_Cast01 {
    public static void main(String[] args) {
        Thread threadDaemon = new Thread(new Runnable() {
            @Override
            public void run() {
               for (;;){}
            }
        });
        // 设置为守护线程 可以和主线程一起结束
        threadDaemon.setDaemon(true);
        // 启动子线程
        threadDaemon.start();
        System.out.println(Thread.currentThread()+"threadDaemon is Over");
    }
}
