package com.wxw.service.impl;

import com.wxw.dao.RedisCachedDao;
import com.wxw.domain.Address;
import com.wxw.domain.Person;
import com.wxw.manager.conf.RedisCacheConfig;
import com.wxw.service.RedisCachedService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author ：wxw.
 * @date ： 15:01 2021/3/1
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@Service
public class RedisCachedServiceImpl implements RedisCachedService {

    @Resource
    private RedisCachedDao redisCachedDao;

    @Override
    public String getString() {
        return redisCachedDao.getString();
    }

    /**
     * 将方法参数结果缓存起来，下一次方法执行参数相同时，将不执行方法，返回缓存中的结果
     * unless = "#result==null" 条件符合则不缓存
     *
     * @return
     */
    @Cacheable(value = RedisCacheConfig.REDIS_KEY_DATABASE, key = "'redis:person:'+#id", unless = "#result==null")
    @Override
    public Person getPerson(Integer id) {
        Address address = new Address(1L,"甘肃省","北京市","房山区","甘肃省 北京市 房山区");
        Person person = new Person(1L, "刘备", 18, new Date(), LocalDate.now(), address);
//        Person person = new Person(1L, "刘备", 18, new Date(), address);
        return person;
    }

    /**
     * 该方法执行时会清空指定的缓存。一般使用在更新或删除方法上
     * @param id
     * @return
     */
    @CacheEvict(value = RedisCacheConfig.REDIS_KEY_DATABASE, key = "'redis:person:'+#id")
    @Override
    public String deletePerson(Integer id) {

        return "清除Redis 编号："+id+ "缓存成功！！！";
    }

    /**
     * 每次执行时都会把返回结果存入缓存中。一般使用在新增方法上
     * @param id
     * @return
     */
    @CachePut(value = RedisCacheConfig.REDIS_KEY_DATABASE, key = "'redis:person:'+#id")
    @Override
    public Person putPerson(Integer id) {
        Address address = new Address(1L,"北京","北京市","房山区","甘肃省 北京市 房山区");
        Person person = new Person(1L, "诸葛亮", 18, new Date(), LocalDate.now(), address);
//        Person person = new Person(1L, "刘备", 18, new Date(), address);
        return person;
    }

    /**
     * spring中的缓存技术--Caching
     * https://www.jianshu.com/p/9f9aad8d41fb
     * @param id
     * @return
     */
    @Override
    @Caching(cacheable = {
            @Cacheable(value = RedisCacheConfig.REDIS_KEY_DATABASE,key = "'redis:person:'+#id"),  // 缓存
    },put = {
            @CachePut(value = RedisCacheConfig.REDIS_KEY_DATABASE,key = "'redis:person:'+#id"),   // 更新
    },evict = {
            @CacheEvict(value = RedisCacheConfig.REDIS_KEY_DATABASE,key = "'redis:person:'+#id"), // 清除缓存
    })
    public Person conditionPerson(Integer id) {
        Address address = new Address(1L,"北京","北京市","房山区","甘肃省 北京市 房山区");
        Person person = new Person(1L, "诸葛亮", 18, new Date(), LocalDate.now(), address);
        return person;
    }
}
