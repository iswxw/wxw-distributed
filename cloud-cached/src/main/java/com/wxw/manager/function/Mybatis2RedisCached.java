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
        logger.info("[mybatis-Redis-Cache] keyId ==> {}",keyId);
        this.keyId = keyId;
    }

    @Override
    public String getId() {
        return this.keyId;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (value != null) {
            logger.debug("[存入缓存] key = {},value = {}",key.toString(),value);
            redisTemplate.opsForHash().put(this.keyId, key.toString(), value);
        }
    }

    @Override
    public Object getObject(Object key) {
        if (key != null) {
            Object value = redisTemplate.opsForHash().get(this.keyId, key.toString());
            logger.debug("[获取缓存] key = {}, value = {}",key.toString(),value);
            return value;
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (key != null) {
            Long delete = redisTemplate.opsForHash().delete(this.keyId, key.toString());
            logger.debug("[删除缓存] key = {}, value = {}",key.toString(),delete);
            return delete;
        }
        return null;
    }

    @Override
    public void clear() {
        redisTemplate.delete(this.keyId);
        logger.debug("清空缓存 keyId ==> {}",getId());
    }

    @Override
    public int getSize() {
        Long size = redisTemplate.opsForHash().size(this.keyId);
        return size == null ? 0 : size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
