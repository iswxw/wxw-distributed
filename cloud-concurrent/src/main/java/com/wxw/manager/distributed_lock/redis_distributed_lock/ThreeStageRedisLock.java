package com.wxw.manager.distributed_lock.redis_distributed_lock;

import com.wxw.common.tools.OrderNumGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 15:14 2021/2/2
 * @description： 第三阶段 保证 加锁+过期时间原子性
 * @link:
 * @version: v_0.0.1
 */
@Component
public class ThreeStageRedisLock {

    @Resource
    private RedisTemplate redisTemplate;
    public static final String uniqueId = OrderNumGenerator.getUniqueId();


    /**
     * 基于TwoStageRedisLock 实现分布式锁加锁：是保证 设置锁 和 给锁设置过期时间 是一个原子操作，
     * 避免了 服务宕机、断电导致设置锁过期时间逻辑无法执行，从而无法释放锁，造成死锁
     * 但是面临的问题：如果发送 Full GC 或者 业务耗时长的情况，导致A线程的锁已经到期了并且自动释放，那B线程也会获取锁成功
     * 那等A线程执行完再释放锁时，可能会释放B线程的锁, 导致 勿释放其它线程的锁 和 连环加锁错乱的问题
     * 解决方案：
     * - 在删除（释放）锁之前，需要根据返回值判断获取到的锁是否是当前锁的Key
     * - 锁续约
     */
    public void lock() {
        String uniqueId = OrderNumGenerator.getUniqueId();
        //  分布式锁占坑 同时设置过期时间
        boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uniqueId, 300, TimeUnit.SECONDS);
        if (lock) {
            // TODO: 2021/2/2 加锁成功... 执行业务

            // 突然断电

            // 设置过期时间 30s过期, 必须和加锁一起作为原子性操作
            // redisTemplate.expire("lock", 30, TimeUnit.SECONDS) ;

            // 业务逻辑执行完后 删除Key 释放锁，不能删除别人的锁
            redisTemplate.delete("lock");
        } else {
            // 加锁失败，重试。 synchronized()
            // 休眠100ms重试
            // 自旋
            lock();
        }

    }

    /**
     * 释放锁
     */
    public void unlock() {
        // 业务逻辑执行完后 删除Key 释放锁
        redisTemplate.delete("lock");
    }
}