package com.wxw.common.tools;

import cn.hutool.core.date.DatePattern;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author ：wxw.
 * @date ： 9:52 2020/12/10
 * @description：日期工具类
 * @link:
 * @version: v_0.0.1
 */
public class DateTools {

    /**
     * 将字符串类型转换为日期类型 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static Date string2DateTime(String dateString) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateString, DatePattern.NORM_DATETIME_MS_PATTERN);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳---->yyyy-MM-dd HH:mm:ss
     */
    public static String getLong2YyyyMmDdHhMmSs(long time) {
        return DateFormatUtils.format(time, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将日期时间 转换为 时间戳
     */
    public static Long getDate2Long(Date date) {
        return date.getTime();
    }

}
