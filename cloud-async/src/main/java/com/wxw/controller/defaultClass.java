package com.wxw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/5
 */
@RestController
public class defaultClass {

    @GetMapping("/")
    public String defaultClass(){
        return "骏马是跑出来的，强兵是打出来的";
    }
}
