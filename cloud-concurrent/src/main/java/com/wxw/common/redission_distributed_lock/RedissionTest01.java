package com.wxw.common.redission_distributed_lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 16:34 2020/11/16
 * @description：分布式锁测试
 * @version: v_0.0.1
 */
@Component
public class RedissionTest01 {

    @Resource
    private RedissonClient redissonClient;

    public void test_lock(){

        /**
         * 获取锁
         */
        RLock lock = redissonClient.getLock("-1");

        /**
         * 释放锁
         */
        lock.unlock();
    }

    /**
     * redis 分布式锁的缺陷
     *   redis锁可以比较完美地解决高并发的时候分布式系统的线程安全性的问题，但是这种锁机制也并不是完美的。
     *   在哨兵模式下，客户端对master节点加了锁，此时会异步复制给slave节点，此时如果master发生宕机，主备切换，slave变成了master。
     *   因为之前是异步复制，所以此时正好又有个线程来尝试加锁的时候，就会导致多个客户端对同一个分布式锁完成了加锁操作，这时候业务上会出现脏数据了。
     *   关于redis的相关知识，大家可以访问老猫之前的一些文章，包括redis的哨兵模式、持久化等等
     * @param args
     */
    public static void main(String[] args) {

    }
}
