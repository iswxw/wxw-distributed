package com.wxw.common.redission_distributed_lock;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.config.Config;

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
    public static void main(String[] args) {
        String lockKey = "myLock";
        Config config = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6379");
        Config config2 = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6380");
        Config config3 = new Config();
        config.useSingleServer().setPassword("123456").setAddress("redis://127.0.0.1:6381");

        RLock lock = Redisson.create(config).getLock(lockKey);
        RLock lock2 = Redisson.create(config2).getLock(lockKey);
        RLock lock3 = Redisson.create(config3).getLock(lockKey);

        RedissonRedLock redLock = new RedissonRedLock(lock, lock2, lock3);

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
