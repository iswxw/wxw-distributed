package com.wxw.common.redission_distributed_lock;

import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 15:26 2020/12/28
 * @description：红锁测试
 * @link:
 * @version: v_0.0.1
 */
public class RedissionRedLockTest {

    /**
     * Redis 通过红锁解决了 主从节点下异步 同步数据导致锁丢失问题
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        String lockKey = "myLock";
        Config config = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6379");
        Config config2 = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6380");
        Config config3 = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6381");

        /**
         * 大家都知道，如果负责储存这个分布式锁的Redis节点宕机以后，而且这个锁正好处于锁住的状态时，这个锁会出现锁死的状态。为了避免这种情况的发生，
         * Redisson内部提供了一个监控锁的看门狗，它的作用是在Redisson实例被关闭前，不断的延长锁的有效期。默认情况下，看门狗的检查锁的超时时间是30秒钟，
         * 也可以通过修改Config.lockWatchdogTimeout来另行指定
         */
        config.setLockWatchdogTimeout(30000L); // 设置为30s时检查一次，key是否过期


        RLock lock1 = Redisson.create(config).getLock(lockKey);
        RLock lock2 = Redisson.create(config2).getLock(lockKey);
        RLock lock3 = Redisson.create(config3).getLock(lockKey);

        /**
         * =================== 8.4. 红锁（RedLock）==============
         * 基于Redis的Redisson红锁RedissonRedLock对象实现了Redlock介绍的加锁算法。该对象也可以用来将多个RLock对象关联为一个红锁，
         * 每个RLock对象实例可以来自于不同的Redisson实例。
         * Redis 通过 红锁 解决了 主从节点下异步 同步数据导致锁丢失问题
         */
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);

        /**
         * ================8.3. 联锁（MultiLock）================
         */
        RedissonMultiLock multiLock = new RedissonMultiLock(lock1, lock2, lock3);
        // 同时加锁：lock1 lock2 lock3
        // 所有的锁都上锁成功才算成功

        // 给lock1，lock2，lock3加锁，如果没有手动解开的话，10秒钟后将会自动解开
        multiLock.lock(10, TimeUnit.SECONDS);

        // 为加锁等待100秒时间，并在加锁成功10秒钟后自动解开
        boolean res = multiLock.tryLock(100, 10, TimeUnit.SECONDS);


        try {
            /**
             * 加锁
             */
            redLock.lock();

        } finally {
            /**
             * 释放锁
             */
            redLock.unlock();
        }
    }
}
