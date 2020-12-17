package com.wxw.jvm.monitoring;


/**
 * @author ：wxw.
 * @date ： 11:13 2020/12/17
 * @description：线程死循环demo
 * @link:
 * @version: v_0.0.1
 */
public class JConsoleDeadCycleDemo {

    public static void main(String[] args) {
        int n = 0;
        while (true) {
            System.out.println("n = " + ++n);
        }
    }
}
