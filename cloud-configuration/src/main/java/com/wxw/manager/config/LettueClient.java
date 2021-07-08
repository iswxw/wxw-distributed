package com.wxw.manager.config;


import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author ：wxw.
 * @date ： 15:58 2020/12/14
 * @description：基于lettue实现redis客户端
 * @link:
 * @version: v_0.0.1
 */
public class LettueClient {

    public static RedisClient LettueClient(){
        RedisURI redisURI = RedisURI.builder()
                .withHost("localhost")
                .withDatabase(6)
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        return  RedisClient.create(redisURI);
    }
}
