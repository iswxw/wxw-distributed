package com.wxw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author ：wxw.
 * @date ： 17:32 2020/12/14
 * @description：Redis 类接口处理
 * @link:
 * @version: v_0.0.1
 */
@Controller
@RequestMapping
public class RedisController {

    @GetMapping(value = "/redis")
    @ResponseBody
    public Map<String, Object> redisTest(String type){

        return null;
    }
}
