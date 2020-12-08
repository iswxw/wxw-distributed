package com.wxw.thread;

/**
 * @ Author ：wxw.
 * @ Date ： 19:52 2020/7/4
 * @ Description：等待线程执行中止
 * @ Version:   v_0.0.1
 */
public class Thread_Join_Cast01 {

    public static void main(String[] args) throws InterruptedException {


        Thread A =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("子线程A结束");
            }
        },"threadA");

       Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("子线程B结束");
            }
        },"threadB");

       //启动子线程
        A.start();
        B.start();
        System.out.println("=====OVER======");

        // 等待子线程执行结束返回
        B.join();  // 线程加入，等待B线程执行结束才可以执行后面的操作
        A.join();

    }




}
