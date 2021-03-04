package com.wxw.manager.function;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ：wxw.
 * @date ： 14:42 2021/3/4
 * @description：二级缓存 过期时间 实现 (待实现)
 * @link:
 * @version: v_0.0.1
 */
@Deprecated
public class ScheduleMybatis2RedisCached implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;
    private long flushInterval = 10000; // 缓存刷新时间, 单位毫秒
    private Cache delegate; // 委派缓存类

    private static RedisTemplate<Object, Object> redisTemplate;

    public static void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        ScheduleMybatis2RedisCached.redisTemplate = redisTemplate;
    }

    public ScheduleMybatis2RedisCached(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id can not be null");
        }
        this.id = id;
        this.delegate = new ScheduleMybatis2RedisCached(id);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        // 记录过期时间
        long timeout = System.currentTimeMillis() + flushInterval;
        redisTemplate.opsForHash().entries(getKey());
        delegate.putObject(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        return delegate.removeObject(key);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    protected String getKey() {
        return null;
    }
}
