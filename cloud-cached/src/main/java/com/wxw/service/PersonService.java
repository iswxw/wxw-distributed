package com.wxw.service;


import com.wxw.domain.Person;

import java.util.List;

/**
 * @author ：wxw.
 * @date ： 15:12 2021/3/2
 * @description：
 * @link:
 * @version: v_0.0.1
 */
public interface PersonService {

    /**
     * 查询引擎集合
     * @return
     */
    List<Person> queryPersonList();

    /**
     * 根据personId获取数据
     * @param personId
     * @return
     */
    Person selectById(Integer personId);

}
