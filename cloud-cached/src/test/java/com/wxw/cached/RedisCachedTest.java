package com.wxw.cached;

import com.wxw.domain.Person;
import com.wxw.service.RedisCachedService;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 15:07 2021/3/1
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@SpringBootTest
@RunWith(SpringRunner.class) // 将bean注入到Spring容器
@Slf4j
public class RedisCachedTest {

    @Resource
    private RedisCachedService redisCachedService;

    private long timeDistance = 0;


    // 每次执行时都会把返回结果存入缓存中。一般使用在新增方法上 相当于更新缓存
    @Test
    public void test_3(){
        Person person = redisCachedService.putPerson(2);
        log.info(" person = {}", person);
    }

    // 设置缓存 Cacheable
    @Test
    public void test_1(){
        Person person = redisCachedService.getPerson(2);
        log.info(" person = {}", person);
    }
    // 清除缓存
    @Test
    public void test_2(){
        String deletePerson = redisCachedService.deletePerson(1);
        log.info(" deletePerson = {}", deletePerson);
    }


    @Before
    public void test_before(){
        timeDistance = System.currentTimeMillis();
    }

    @After
    public void test_after(){
        System.err.printf("执行总耗时：%s \n",( System.currentTimeMillis() - timeDistance) + "ms");
    }
}
