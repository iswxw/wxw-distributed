package com.wxw.manager.function;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ：wxw.
 * @date ： 9:07 2021/3/3
 * @description：基于Redis实现Mybatis二级缓存自定义
 * @link:
 * @version: v_0.0.1
 */
public class Mybatis2RedisCached implements Cache {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private static RedisTemplate<Object, Object> redisTemplate;

    public static void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        Mybatis2RedisCached.redisTemplate = redisTemplate;
    }

    private String keyId;

    public Mybatis2RedisCached(final String keyId) {
        if (keyId == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.info("Redis Cache keyId " + keyId);
        this.keyId = keyId;
    }

    @Override
    public String getId() {
        return this.keyId;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (value != null) {
            redisTemplate.opsForHash().put(this.keyId, key.toString(), value);
        }
    }

    @Override
    public Object getObject(Object key) {
        if (key != null) {
            return redisTemplate.opsForHash().get(this.keyId, key.toString());
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (key != null) {
            return redisTemplate.opsForHash().delete(this.keyId, key.toString());
        }
        return null;
    }

    @Override
    public void clear() {
        redisTemplate.delete(this.keyId);
    }

    @Override
    public int getSize() {
        return redisTemplate.opsForHash().size(this.keyId).intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
