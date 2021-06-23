package com.sms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jared
 * @date 2021/6/6 16:49
 */
public class StringUtils {

    /**
     * 用户账号必须长度为12位
     * @param userNumber
     * @return
     */
    public static boolean checkUserNumber(String userNumber){
        boolean flag;
        try {
            Pattern regex = Pattern.compile("\\d{12}");
            flag = regex.matcher(userNumber).matches();

        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 以字母开头，长度在6~18之间，只能包含字母、数字和下划线
     * @param password 密码
     * @return
     */
    public static boolean checkPassword(String password){
        boolean flag;
        try{
            Pattern regex = Pattern.compile("^[a-zA-Z]\\w{5,17}$");
            Matcher matcher = regex.matcher(password);
            flag = matcher.matches();
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码，11位数字，1开通，第二位数必须是3456789这些数字之一 *
     * @param mobileNumber
     * @return
     */
    public static boolean checkPhoneNumber(String mobileNumber) {
        boolean flag;
        try {
            Pattern regex = Pattern.compile("^1[3456789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


    /**
     * 验证邮箱的正则表达式
     * @param email
     * @return
     */
    public static boolean checkEmail(String email)
    {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 判断给定字符串是否空白串。
     *  空白串是指由空格、制表符、回车符、换行符组成的字符串
     *      若输入字符串为null或空字符串，返回true
     * @param input 输入的信息
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
}
