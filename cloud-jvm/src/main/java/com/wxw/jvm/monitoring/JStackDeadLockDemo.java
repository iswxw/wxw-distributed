package com.wxw.jvm.monitoring;


/**
 * @author ：wxw.
 * @date ： 9:54 2020/12/17
 * @description：jstack分析死锁程序 线程死锁 一个线程要同时拥有两个对象的资源才能进行下一步操作；
 * @link: https://blog.csdn.net/e5yrt2/article/details/106352539
 * @version: v_0.0.1
 */
public class JStackDeadLockDemo{
    public static void main(String[] args) {
        /**
         * 死锁是两个或两个以上的进程在执行过程中，因为抢夺资源导致相互等待的现象
         * 若无外力干预，它们将无法推进下去
         */
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLock(lockA,lockB),"Thread-A").start();
        new Thread(new HoldLock(lockB,lockA),"Thread-B").start();
        System.out.println();
        /**
         * 怎么定位死锁问题
         * linux         ps -ef|grep xxx  / ls-l
         * windows下Java程序运行，可以模拟ps命令
         *   1、jps =java ps      jps -l 查看进程号
         *   2、jstack 进程号
         */
    }
}
class HoldLock implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLock(String lockA,String lockB){
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockA + "\t 尝试获取：" + lockB);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockB + "\t 尝试获取：" + lockA);
            }
        }
    }
}
