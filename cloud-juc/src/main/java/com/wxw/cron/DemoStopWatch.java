package com.wxw.cron;

import org.springframework.util.StopWatch;

/**
 * @author ：wxw.
 * @date ： 11:02 2021/2/24
 * @description：
 * @link:
 * @version: v_0.0.1
 */
public class DemoStopWatch {
    public static void main(String[] args) {
        StopWatch watch = new StopWatch("测试");
        watch.start();
        watch.stop();
        System.out.println(watch.prettyPrint());
    }
}
