package com.wxw.thread;

/**
 * @ Author ：wxw.
 * @ Date ： 16:45 2020/6/27
 * @ Description：实现接口实现多线程创建
 * @ Version:   v_0.0.1
 */
public class Thread_Cast02 {

    public static void main(String[] args) {
        RunableTask runableTask = new RunableTask();
        new Thread(runableTask).start();
        new Thread(runableTask).start();
    }
}


class RunableTask implements Runnable{

    public void run() {
        System.out.println("我是一个子线程：MyThread");
    }
}
