package com.wxw.controller;

import com.wxw.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/5
 */
@Slf4j
@RequestMapping("/sync")
@RestController
public class SyncController {

    @Resource
    private AsyncService aSyncService;

    /**
     * 同步处理
     *
     */
    @GetMapping("/sync-1")
    public String sync_1(){
        aSyncService.sync_1();
        log.info(Thread.currentThread().getName()+"==========主线程名");
        return "同步,正在解析......";
    }



}
