package com.wxw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author ：wxw.
 * @date ： 15:01 2021/3/12
 * @description：Session 缓存
 * @link:
 * @version: v_0.0.1
 */
@Slf4j
@RestController
@RequestMapping("session")
public class SessionCachedController {

    @Value("${server.port:18080}")
    private String serverPort;

    /**
     * @url: http://localhost:8080/session/set
     * @param session
     * @return
     */
    @GetMapping("/set")
    public String setSession(HttpSession session){
        session.setAttribute("name","Java半颗糖");
        log.info(" session = {}", session.getAttribute("name"));
        return String.valueOf(serverPort);
    }

    /**
     * @url:http://localhost:8080/session/get
     * @param session
     * @return
     */
    @GetMapping("/get")
    public String get(HttpSession session) {
        return session.getAttribute("name") + ":" + serverPort;
    }
}
