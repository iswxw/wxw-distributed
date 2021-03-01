package com.wxw.controller;

import com.wxw.domain.Person;
import com.wxw.service.RedisCachedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 14:59 2021/3/1
 * @description：Redis的缓存处理
 * @link:
 * @version: v_0.0.1
 */
@RestController
public class RedisCachedController {

    @Resource
    private RedisCachedService redisCachedService;
    
    @GetMapping("cache")
    public String getString(){
        return redisCachedService.getString();
    }

    /**
     * 删除
     * @return
     */
    @GetMapping("person/delete/{id}")
    public String deletePerson(@PathVariable("id")Integer id){
        return redisCachedService.deletePerson(id);
    }

    /**
     * 添加缓存
     * @param id
     * @return
     */
    @GetMapping("person/get/{id}")
    public Person getPerson(@PathVariable("id")Integer id){
        return redisCachedService.getPerson(id);
    }


    /**
     * Put 修改
     * @return
     */
    @GetMapping("person/put/{id}")
    public Person putPerson(@PathVariable("id")Integer id){
        return redisCachedService.putPerson(id);
    }

}
