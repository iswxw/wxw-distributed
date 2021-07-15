package com.wxw.juc.hashmap;

import java.util.HashMap;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/15
 */
public class DemoHashMap {
    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        // 返回 null
        String put = map.put("a", "A");
        System.out.println("put = " + put);
        System.out.println("map = " + map);
    }
}
