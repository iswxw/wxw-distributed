package com.wxw.common.tools;

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
     * 将字符串类型转换为日期类型
     */
   public Date string2DateTime(String dateString){
       Date date = null;
       try {
           date = DateUtils.parseDate(dateString,null);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       return date;
   }


}
