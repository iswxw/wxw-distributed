package com.wxw.manager.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author ：wxw.
 * @date ： 16:41 2020/12/14
 * @description：Jedis 客户端
 * @link:
 * @version: v_0.0.1
 */
public class JedisClient {

    public static Jedis  JedisClient(){
        JedisPool jedisPool = new JedisPool();
        Jedis resource = jedisPool.getResource();
        return resource;
    }
}
