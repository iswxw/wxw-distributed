package com.wxw.common.redis_distributed_lock;

import com.wxw.common.manager.tools.OrderNumGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wxw.
 * @date ： 15:17 2021/2/2
 * @description：第四阶段 不能删除别人的锁
 * @link:
 * @version: v_0.0.1
 */
@Component
public class FourStageRedisLock {

    @Resource
    private RedisTemplate redisTemplate;
    public static final String uniqueId = OrderNumGenerator.getUniqueId();

    /**
     * 1. 占分布式锁，去redis占坑
     * 分布式锁加锁
     * 面临的问题：加入刚拿到锁后正在执行业务逻辑时 锁的 value 过期了,此时，其他人拿到锁，并设置了新值，
     *          但是该线程执行完业务逻辑，并删除了key对应的别人刚加的锁，（删除锁不是原子操作）
     * 解决方案：删除锁必须保证原子性。使用redis+Lua脚本。
     */
    public void lock() {
        boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uniqueId);
        if (lock) {
            // TODO: 2021/2/2 加锁成功... 执行业务

            // 突然断电

            // 设置过期时间 30s过期
            redisTemplate.expire("lock", 30, TimeUnit.SECONDS) ;

            String lockValue = redisTemplate.opsForValue().get("lock").toString();
            if(uniqueId.equals(lockValue)) {
                // 删除我自己的锁,不能删除别人锁，但是
                redisTemplate.delete("lock");
            }
            // 业务逻辑执行完后 删除Key 释放锁
            // redisTemplate.delete("lock");
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
    public void unlock(){
        // 业务逻辑执行完后 删除Key 释放锁
        Object lock = redisTemplate.opsForValue().get("lock");
        if (uniqueId.equals(lock)){
            redisTemplate.delete("lock");
        }
    }
}