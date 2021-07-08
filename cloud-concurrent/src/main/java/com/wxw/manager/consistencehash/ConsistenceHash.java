package com.wxw.manager.consistencehash;

import net.rubyeye.xmemcached.HashAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wxw
 * @date: 2020-12-27-23:49
 * @description: 一致性Hash 算法
 */
public class ConsistenceHash {

    // 物理节点 多个
    private List<String> realNodes = new ArrayList<>();

    // 虚拟节点的数量 用户可指定
    private int virtualNums = 100;

    // 对应关系存储 物理节点——hash值
    private Map<String,List<Integer>> real2VirtualMap = new HashMap<>();



    public ConsistenceHash(int virtualNums){
        this.virtualNums = virtualNums;
    }

    public void addServer(String node){
        this.realNodes.add(node);
        // 虚拟 虚拟节点， 虚拟多少个？
        String vnode = null;
        List<Integer> virtualList = new ArrayList<>();
        // 物理节点与 虚拟节点的hash值对应关系
        real2VirtualMap.put(node,virtualList);
        for (int count = 0; count < this.virtualNums; count++) {
            vnode = "vvv-" + node + "-" + count;
            /**
             * 把虚拟节点放到环上
             *   1. 求hash值
             *   2. 放到hash环上
             * Hash 算法 FNV1_32_HASH
             */
            long hashValue = HashAlgorithm.FNV1_32_HASH.hash(vnode);
            // 保存物理节点和虚拟节点的对应关系
            virtualList.add((int)hashValue);
            // 放到环上，环就是双向链表


        }
    }



}
