package com.wxw.manager.local_cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/25
 */
public class DemoLocalCache {

    // 全局变量缓存
    private static Map<String, Integer> ageCacheMap = new HashMap<>();

    // 根据名称获取年龄
    public Integer getAgeByName(String name) {
        // 缓存命中
        int cacheAge = ageCacheMap.get(name);
        if (cacheAge != 0) {
            return cacheAge;
        }
        // 缓存中不存在，则从数据库中查询
        Integer age = getAgeFromDB(name);
        // 添加到缓存中
        ageCacheMap.put(name, age);
        return age;
    }

    // 从数据库中获取
    public Integer getAgeFromDB(String name) {
        // TODO: 省略查询逻辑
        return 1;
    }

    public static void main(String[] args) {

    }
}
