package com.wxw.redis;

import com.wxw.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 10:02 2021/3/3
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@Slf4j
public class RedisTemplateTest extends BaseTest {

    @Resource
    private RedisTemplate redisTemplate;
    
    @Test
    public void test_1(){
        redisTemplate.opsForValue().set("key","value");
        Object key = redisTemplate.opsForValue().get("key");
        log.info(" key = {}", key);
    }
}
