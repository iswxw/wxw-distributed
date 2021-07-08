package com.wxw.manager.distributed_lock.redission_annotation_lock;

import com.wxw.manager.distributed_lock.redission_annotation_lock.base.BaseLock;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ Author ：wxw.
 * @ Date ： 16:10 2020/9/4
 * @ Description：Redis一般锁实现
 * @ Version:   v_0.0.1
 */
@Component
public class RedisLock extends BaseLock {

    public static final String REDIS_LOCK_KEY = "lockhhf";

    public static final String serverPort = "8080";

    /**
     * 并发情况下的bug
     * https://blog.csdn.net/qq_41893274/article/details/110428877
     *
     * @return
     */
    public String testLock() {
        String value = UUID.randomUUID().toString() + Thread.currentThread().getName();
        RLock redissonLock = redissonClient.getLock(REDIS_LOCK_KEY);
        redissonLock.lock();
        try {
            String result = stringRedis.opsForValue().get("goods:001");
            int goodsNumber = result == null ? 0 : Integer.parseInt(result);

            if (goodsNumber > 0) {
                int realNumber = goodsNumber - 1;
                stringRedis.opsForValue().set("goods:001", realNumber + "");
                System.out.println("你已经成功秒杀商品，此时还剩余：" + realNumber + "件" + "\t 服务器端口: " + serverPort);
                return "你已经成功秒杀商品，此时还剩余：" + realNumber + "件" + "\t 服务器端口: " + serverPort;
            } else {
                System.out.println("商品已经售罄/活动结束/调用超时，欢迎下次光临" + "\t 服务器端口: " + serverPort);
            }
            return "商品已经售罄/活动结束/调用超时，欢迎下次光临" + "\t 服务器端口: " + serverPort;

        } finally {
            /**
             *  attempt to unlock lock, not locked by current thread by node
             *      id: 9f178836-f7e1-44fe-a89d-2db52f399c0d thread-id: 22
             * 还在持有锁的状态，并且是当前线程持有的锁再解锁
             */
            if (redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()) {
                redissonLock.unlock();
            }

        }
    }


    /**
     * 加锁
     *
     * @param id
     * @return
     */
    public boolean lock(String id) {
        stringRedis.opsForValue().set("wxw", "Redis分布式锁设置数据_" + id, 30, TimeUnit.SECONDS);
        String wxw = stringRedis.opsForValue().get("wxw");
        logger.info("wxw = {}", wxw);
        return true;
    }
}
