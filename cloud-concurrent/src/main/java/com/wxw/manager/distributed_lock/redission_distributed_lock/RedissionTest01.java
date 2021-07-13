package com.wxw.manager.distributed_lock.redission_distributed_lock;

import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 16:34 2020/11/16
 * @description：分布式锁测试
 * @link: https://github.com/redisson/redisson/wiki/
 */
@Component
public class RedissionTest01 {

    @Resource
    private RedissonClient redissonClient;

    public void test_lock() throws InterruptedException {


        /**
         * ====================可重入锁（Reentrant Lock）=========================
         * 获取锁
         */
        RLock lock = redissonClient.getLock("anyLock");

        /**
         * 加锁以后10秒钟自动解锁
         * 无需调用unlock方法手动解锁
         */
        lock.lock(10, TimeUnit.SECONDS);

        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        if (res) {
            try {
                // TODO: 2021/2/3 执行业务逻辑
            } finally {
                lock.unlock();
            }
        }

        /**
         * 上面情况也支持异步加锁和释放锁
         */
        lock.lockAsync();
        lock.lockAsync(10, TimeUnit.SECONDS);
        Future<Boolean> resAsync = lock.tryLockAsync(100, 10, TimeUnit.SECONDS);

        /**
         * ===================公平锁（Fair Lock）===================
         */
        RLock fairLock = redissonClient.getFairLock("anyLock");
        // 最常见的使用方法
        fairLock.lock();

        /**
         * =================== 读写锁（ReadWriteLock）==============
         * 基于Redis的Redisson分布式可重入读写锁RReadWriteLock Java对象实现了java.util.concurrent.locks.ReadWriteLock接口。
         * 其中读锁和写锁都继承了RLock接口。
         * 分布式可重入读写锁允许同时有多个读锁和一个写锁处于加锁状态。
         */
        RReadWriteLock rwlock = redissonClient.getReadWriteLock("anyRWLock");
        // 最常见的使用方法
        rwlock.readLock().lock();
        // 或
        rwlock.writeLock().lock();

        // 10秒钟以后自动解锁
        // 无需调用unlock方法手动解锁
        rwlock.readLock().lock(10, TimeUnit.SECONDS);
        // 或
        rwlock.writeLock().lock(10, TimeUnit.SECONDS);
        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
        boolean res1 = rwlock.readLock().tryLock(100, 10, TimeUnit.SECONDS);
        // 或
        boolean res2 = rwlock.writeLock().tryLock(100, 10, TimeUnit.SECONDS);

        /**
         * ============== 8.8. 闭锁（CountDownLatch）===============
         * 基于Redisson的Redisson分布式闭锁（CountDownLatch）Java对象RCountDownLatch采用了
         * 与 java.util.concurrent.CountDownLatch相似的接口和用法。
         */
        RCountDownLatch latch1 = redissonClient.getCountDownLatch("anyCountDownLatch");
        latch1.trySetCount(1);
        latch1.await();
        // 在其他线程或其他JVM里
        RCountDownLatch latch2 = redissonClient.getCountDownLatch("anyCountDownLatch");
        latch2.countDown();


        /**
         * 释放锁
         */
        lock.unlock();
    }

    /**
     * redis 分布式锁的缺陷
     * redis锁可以比较完美地解决高并发的时候分布式系统的线程安全性的问题，但是这种锁机制也并不是完美的。
     * 在哨兵模式下，客户端对master节点加了锁，此时会异步复制给slave节点，此时如果master发生宕机，主备切换，slave变成了master。
     * 因为之前是异步复制，所以此时正好又有个线程来尝试加锁的时候，就会导致多个客户端对同一个分布式锁完成了加锁操作，这时候业务上会出现脏数据了。
     * 关于redis的相关知识，大家可以访问老猫之前的一些文章，包括redis的哨兵模式、持久化等等
     *
     * @param args
     */
    public static void main(String[] args) {

    }
}
