package com.wxw.thread;

/**
 * @ Author ：wxw.
 * @ Date ： 18:23 2020/6/27
 * @ Description：wait中断
 * @ Version:   v_0.0.1
 * 证明理论：
 * 当一个线程调用共享对象的wait()方法时被阻塞挂起后，如果其它线程中断了该线程，则线程会抛出 被中断异常并退出
 */
public class Thread_Wait_Cast02 {
    private static volatile Object resource = new Object();

    public static void main(String[] args) throws InterruptedException {
        //创建线程
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("开始...");
                    //阻塞当前线程
                    synchronized (resource){
                        resource.wait();
                    }
                    System.out.println("结束...");
                } catch (InterruptedException e) {
                    System.out.println("中断异常:"+e);
                }
            }
        });

        threadA.start();

        Thread.sleep(1000);

        System.out.println("开始 中断 threadA----");
        threadA.interrupt();
        System.out.println("结束 中断 threadA----");
    }
}
