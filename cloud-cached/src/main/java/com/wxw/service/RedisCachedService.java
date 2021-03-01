package com.wxw.service;

import com.wxw.domain.Person;

/**
 * @author ：wxw.
 * @date ： 15:00 2021/3/1
 * @description：
 * @link:
 * @version: v_0.0.1
 */
public interface RedisCachedService {

    /**
     * 获取用户名
     * @return
     */
    String getString();

    /**
     * 获取用户信息
     * @return
     */
    Person getPerson(Integer id);

    /**
     * 删除个人信息
     * @return
     */
    String deletePerson(Integer id);


    /**
     * 修改个人信息
     * @param id
     * @return
     */
    Person putPerson(Integer id);

    /**
     *
     */
    Person conditionPerson(Integer id);
}
