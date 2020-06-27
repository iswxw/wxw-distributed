package com.wxw.thread;

/**
 * @ Author ：wxw.
 * @ Date ： 16:39 2020/6/27
 * @ Description：继承的方式实现多线程
 * @ Version:   v_0.0.1
 */
public class Thead_Cast01 {

    public static void main(String[] args) {
        //创建线程
        MyThread myThread = new MyThread();
        //启动线程
        myThread.run();
    }
}


class MyThread extends Thread {

    @Override
    public void run() {
        // super.run();  // 手动调用父类线程
        System.out.println("我是一个子线程：MyThread");
    }
}
