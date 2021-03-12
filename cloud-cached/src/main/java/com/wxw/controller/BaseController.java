package com.wxw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：wxw.
 * @date ： 17:15 2021/3/11
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@RestController
public class BaseController {

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnNoFavicon() { }
}
