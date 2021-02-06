package com.wxw.common.redis_distributed_lock;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author ：wxw.
 * @date ： 15:17 2021/2/2
 * @description：第五阶段 确保加锁和解锁都是原子性—— 保证加锁【占位+过期时间】和删除锁【判断+删除】的原子性
 * @link:
 * @version: v_0.0.1
 */
@Component
public class FiveStageRedisLock {

    @Resource
    private RedisTemplate redisTemplate;

    private static final Long SUCCESS = 1L;
    private static String script1 = "if redis.call('setNx',KEYS[1],ARGV[1])  then " +
            "   if redis.call('get',KEYS[1])==ARGV[1] then " +
            "      return redis.call('expire',KEYS[1],ARGV[2]) " +
            "   else " +
            "      return 0 " +
            "   end " +
            "end";
    private RedisScript<String> lockRedisScript1 = new DefaultRedisScript<>(script1, String.class);
    private static String script2 = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private RedisScript<String> unLockRedisScript = new DefaultRedisScript<>(script2, String.class);

    /**
     * 1. 占分布式锁，去redis占坑 获取锁——设置值并设定过期时间是一个原子操作
     * 分布式锁加锁
     * 面临的问题：加入刚拿到锁后正在执行业务逻辑时锁的 value 过期了,此时，其他人拿到锁，并设置了新值，
     *          但是该线程执行完业务逻辑，并删除了别人刚加的锁，（删除锁不是原子操作）
     * 解决方案：删除锁必须保证原子性。使用redis+Lua脚本。
     *
     *
     *
     * @param lockKey       redis的key
     * @param value         redis的value要求是随机串，防止释放其他请求的锁
     * @param expireTime    redis的key 的过期时间  单位（秒) 防止死锁，导致其他请求无法正常执行业务
     * @return
     */
    public boolean lock(String lockKey, String value, long expireTime) {
        //对非string类型的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        Object result = redisTemplate.execute(lockRedisScript1, Collections.singletonList(lockKey), value, String.valueOf(expireTime));
        return SUCCESS.equals(result);
    }
    /**
     * 释放锁——获取value并删除是一个原子操作
     *
     * @param lockKey   redis的key
     * @param value     redis的value  只有value比对一致，才能确定是本请求 加的锁 才能正常释放
     * @return
     */
    public  boolean unlock(String lockKey, String value) {
        try {
            Object result = redisTemplate.execute(unLockRedisScript, Collections.singletonList(lockKey), value);
            if (SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}