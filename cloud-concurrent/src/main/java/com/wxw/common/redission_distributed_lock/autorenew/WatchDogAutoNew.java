package com.wxw.common.redission_distributed_lock.autorenew;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;


/**
 * @author ：wxw.
 * @date ： 14:24 2021/2/3
 * @description：watchdog自动续约
 * @link:
 * @version: v_0.0.1
 */
public class WatchDogAutoNew {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.setLockWatchdogTimeout(30000L);
        RedissonClient redisson = Redisson.create(config);
        RLock lock = redisson.getLock("anyLock");

        // Redisson WatchDog机制
        //  异步获取结果，如果获取锁成功，则启动定时线程进行锁续约
        // org.redisson.RedissonLock#tryAcquireAsync()

        /**
         * 首先，会先判断在expirationRenewalMap中是否存在了entryName，
         * 这是个map结构，主要还是判断在这个服务实例中的加锁客户端的锁key是否存在，
         * 如果已经存在了，就直接返回；主要是考虑到RedissonLock是可重入锁。
         *
         * 第一次加锁的时候会调用，内部会启动WatchDog
         */
        // org.redisson.RedissonLock#scheduleExpirationRenewal()

        // renewExpirationAsync续约租期 ——每次间隔租期的1/3时间执行
        // org.redisson.RedissonLock#renewExpiration()

        // 续约internalLockLeaseTime(30s)
        // org.redisson.RedissonLock#renewExpirationAsync()
    }
}
