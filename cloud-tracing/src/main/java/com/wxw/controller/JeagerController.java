package com.wxw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 13:44 2021/3/11
 * @description：Jeager 整合 open-tracing 实现链路追踪
 * @link:
 * @version: v_0.0.1
 */
@RestController
public class JeagerController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private int port;

    @GetMapping("/tracing")
    public String tracing() throws InterruptedException {
        Thread.sleep(100);
        return "tracing:"+port;
    }

    @GetMapping("/open")
    public String open() throws InterruptedException {
        ResponseEntity<String> response =
                restTemplate.getForEntity("http://localhost:" + port + "/tracing", String.class);
        Thread.sleep(200);
        return "open " + response.getBody();
    }
}
