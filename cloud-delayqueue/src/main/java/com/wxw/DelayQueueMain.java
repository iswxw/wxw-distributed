package com.wxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ：wxw.
 * @date ： 15:08 2020/12/9
 * @description：延迟队列启动类
 * @link:
 * @version: v_0.0.1
 */

/**
 * 开启springboot的定时任务
 */
@EnableScheduling
@SpringBootApplication
public class DelayQueueMain {
    public static void main(String[] args) {
        SpringApplication.run(DelayQueueMain.class,args);
    }
}
