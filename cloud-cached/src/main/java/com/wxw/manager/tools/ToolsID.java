package com.wxw.manager.tools;

import cn.hutool.core.util.IdUtil;

/**
 * @ Author ：wxw.
 * @ Date ： 17:49 2020/5/28
 * @ Description：ID生成工具
 * @ Version:   v_0.1$
 */
public class ToolsID {


    /**
     * 转换为 字母数据组合的唯一字符串
     * @return
     */
    public static String getLetterAndDateID(){
        return IdUtil.objectId().toUpperCase();
    }


    /**
     * 生成纯数字的按时间生成的唯一ID
     */
    public static String getDate19ID(){
        return IdUtil.createSnowflake(1, 1).nextIdStr();
    }
}
