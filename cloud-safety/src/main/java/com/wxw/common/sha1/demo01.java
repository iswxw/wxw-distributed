package com.wxw.common.sha1;

import com.google.common.hash.Hashing;
import org.apache.commons.codec.Charsets;

/**
 * @author ：weixiaowei.
 * @date ：2022/1/29
 * @description：
 * @version: 1.0.0
 */
public class demo01 {
    public static void main(String[] args) {
        String sb = "maple";
        String sha1Value = Hashing.sha1().hashString(sb, Charsets.UTF_8).toString();
        System.out.println("sha1Value = " + sha1Value);
    }
}
