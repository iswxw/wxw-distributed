package com.wxw.test;

import java.util.Random;

/**
 * 模拟死循环
 * @author: wxw
 * @date: 2020-10-17-14:21
 */
public class DeadCircleDemo {
    public static void main(String[] args) {
        while (true){
            System.out.println(new Random().nextInt(77778888));
        }
    }
}
