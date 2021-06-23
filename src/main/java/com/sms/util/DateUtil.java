package com.sms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jared
 * @date 2021/6/17 22:38
 */
public class DateUtil {

    /**
     * 获取当前年份
     * @return
     */
    public static Integer getCurrentYear(){
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    }
    /**
     * 获取当前月份
     * @return
     */
    public static Integer getCurrentMonth(){
        return Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
    }
    /**
     * 获取当前几号
     * @return
     */
    public static Integer getCurrentDay(){
        return Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
    }
}
