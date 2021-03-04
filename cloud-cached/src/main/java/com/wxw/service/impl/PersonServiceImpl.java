package com.wxw.service.impl;

import com.wxw.dao.PersonMapper;
import com.wxw.domain.Person;
import com.wxw.domain.PersonExample;
import com.wxw.service.PersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：wxw.
 * @date ： 15:12 2021/3/2
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonMapper personMapper;

    @Override
    public List<Person> queryPersonList() {
        PersonExample example = new PersonExample();
        return personMapper.selectByExample(example);
    }

    @Override
    public Person selectById(Integer personId) {
        return personMapper.selectById(personId);
    }
}
