package com.wxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ Author ：wxw.
 * @ Date ： 13:29 2020/6/29
 * @ Description：redis相关并发处理算法
 * @ Version:   v_0.0.1
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class RedisMain8082 {
    public static void main(String[] args) {
        SpringApplication.run(RedisMain8082.class,args);
    }
}
