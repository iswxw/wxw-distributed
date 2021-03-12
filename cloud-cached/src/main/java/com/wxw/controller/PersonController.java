package com.wxw.controller;

import com.wxw.domain.Person;
import com.wxw.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author ：wxw.
 * @date ： 14:49 2021/3/4
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@RestController
@Slf4j
public class PersonController {

    @Resource
    private PersonService personService;

    @GetMapping("person-list")
    public ResponseEntity<List<Person>> queryPersonList(){
        List<Person> personList = personService.queryPersonList();
        if (CollectionUtils.isEmpty(personList)){
             return ResponseEntity.ok(Collections.EMPTY_LIST);
        }
        return ResponseEntity.ok(personList);
    }

    @GetMapping("person-id")
    public ResponseEntity<Person> queryPersonById(@RequestParam("personId") Integer personId){
        Person person = personService.selectById(personId);
        return ResponseEntity.ok(person);
    }
}
