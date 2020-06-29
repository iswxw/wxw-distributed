package com.wxw.thread;

/**
 * @ Author ：wxw.
 * @ Date ： 17:41 2020/6/27
 * @ Description：线程等待 wait()函数
 * @ Version:   v_0.0.1
 *
 * 证明理论1：
 *   当线程对象调用共享对象的wait的方法时，当前线程只会释放当前共享对象的锁，当前线程持有的其他共享对象的监视器锁并不会被释放
 */
public class Thread_Wait_Cast01 {
    // 创建共享资源
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                try {
                    // 获取resourceA的共享资源监视器
                    synchronized (resourceA){
                        System.out.println("threadA get resourceA lock");

                        // 获取resourceB的共享资源监视器
                        synchronized (resourceB){
                            System.out.println("threadA get resourceB lock");
                            //线程A阻塞，并释放获取到的resourceA的锁
                            System.out.println("threadA release resource resourceA lock");
                            resourceA.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("线程中断异常 = " + e);
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            public void run() {
                try {
                    // 休眠1s
                    Thread.sleep(1000);
                    // 获取resourceA的共享资源监视器
                    synchronized (resourceA){
                        System.out.println("threadB get resourceA lock");

                        System.out.println("threadB try get resourceB lock");

                        // 获取resourceB的共享资源监视器
                        synchronized (resourceB){
                            System.out.println("threadB get resourceB lock");
                            //线程B阻塞，并释放获取到的resourceA的锁
                            System.out.println("threadB release resource resourceA lock");
                            resourceA.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("线程中断异常 = " + e);
                }
            }
        });

        // 启动两个线程
        threadA.start();
        threadB.start();

        // 等待两个线程结束
        threadA.join();
        threadB.join();

        System.out.println("执行完成！");

    }
}


