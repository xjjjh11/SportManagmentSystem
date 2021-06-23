package com.sms.util;

import com.sms.usermgmt.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security工具类
 * @author Jared
 * @date 2021/6/3 14:54
 */
public class SecurityUtil {
    /**
     * 私有化构造器
     */
    private SecurityUtil(){}

    /**
     * 获取当前用户信息
     */
    public static User getUserInfo(){
        return (User) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
    }
    /**
     * 获取当前用户ID
     * @return
     */
    public static String getUserId(){
        return getUserInfo().getUserNumber();
    }
    /**
     * 获取当前用户账号
     */
    public static String getUserName(){
        return getUserInfo().getUsername();
    }
}
