package com.wxw.manager.cache_aside;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/25
 */
public class DemoCacheAside {

    private static FIFOCache<String, String> Cache = CacheUtil.newFIFOCache(1000);

    private static FIFOCache<String, String> SoR = CacheUtil.newFIFOCache(1000);

    public static void main(String[] args) {


    }
}
