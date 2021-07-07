package com.wxw.common.tools;

import cn.hutool.core.date.DatePattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ Author ：wxw.
 * @ Date ： 18:11 2020/5/28
 * @ Description：日期工具类
 * @ Version:   v_0.0.1$
 */
@Slf4j
public class ToolsDate {


    /**
     *  CST 格式日期格式化为 String类型的 "yyyy-MM-dd"
     */
    public static String getCst2StrYyyyMmDd(Date date){
        return DateFormatUtils.format(date, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * Strint类型 2019-05-26 =====> Date类型  2019-05-26
     * @param date
     * @return
     */
    public static Date getStr2DateYyyyMmDd(String date) {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DatePattern.NORM_DATE_PATTERN);
            return sdf.parse(date);
        } catch (ParseException e) {
            log.info("[ToolsDate]==》[getStr2DateYyyyMmDd]日期解析异常=>{}",e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getCst2StrYyyyMmDd(new Date()));
        System.out.println(getStr2DateYyyyMmDd("2019-05-26"));
    }
}
