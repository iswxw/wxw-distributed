package com.wxw.thread;

/**
 * @ Author ：wxw.
 * @ Date ： 20:21 2020/7/4
 * @ Description：线程屈服
 * @ Version:   v_0.0.1
 */
public class Thread_Yield_Cast01 implements Runnable {

    Thread_Yield_Cast01(){
        // 创建并启动线程
        Thread thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) {

        new Thread_Yield_Cast01();
        new Thread_Yield_Cast01();
        new Thread_Yield_Cast01();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            // 当i=0时，让出CPU执行权，放弃时间片，进行下一轮调度
            if ((i%5) == 0){
                System.out.println(Thread.currentThread()+"线程 yield 让出CPU");
                // 当前线程让出CPU执行权，放弃时间片，进行下一轮的调度
                Thread.yield();
            }
        }
        System.out.println("=======OVER============");
    }
}


