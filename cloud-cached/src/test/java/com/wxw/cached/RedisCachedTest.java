package com.wxw.cached;

import com.wxw.BaseTest;
import com.wxw.domain.Person;
import com.wxw.service.RedisCachedService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 15:07 2021/3/1
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@Slf4j
public class RedisCachedTest extends BaseTest {

    @Resource
    private RedisCachedService redisCachedService;

    // 设置缓存 Cacheable
    @Test
    public void test_1(){
        Person person = redisCachedService.getPerson(1);
        log.info(" person = {}", person);
    }
    // 清除缓存
    @Test
    public void test_2(){
        String deletePerson = redisCachedService.deletePerson(1);
        log.info(" deletePerson = {}", deletePerson);
    }

    // 每次执行时都会把返回结果存入缓存中。一般使用在新增方法上 相当于更新缓存
    @Test
    public void test_3(){
        Person person = redisCachedService.putPerson(1);
        log.info(" person = {}", person);
    }
}
