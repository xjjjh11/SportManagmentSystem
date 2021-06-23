package com.sms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 状态码和响应JSON数据
 * @author Jared
 * @date 2021/6/3 11:59
 */
@Slf4j
public class ResultUtil {

    /**
     * 请求成功
     */
    public final static Integer SUCCESS = 200;
    /**
     * 错误请求，用户必填信息不能为空
     */
    public final static Integer USER_INFO_NULL = 410;
    /**
     * 错误请求，用户账号长度错误
     */
    public final static Integer USER_NUMBER_ERROR = 411;
    /**
     * 错误请求，密码不符合要求
     */
    public final static Integer PASSWORD_ERROR = 412;
    /**
     * 错误请求，手机号填写错误
     */
    public final static Integer PHONE_NUMBER_ERROR = 413;
    /**
     * 错误请求，该用户已存在
     */
    public final static Integer USER_EXISTED = 414;
    /**
     * 服务器请求错误
     */
    public final static Integer SERVER_ERROR = 500;

    /**
     * 私有化构造器
     */
    private ResultUtil(){}

    /**
     * 使用response输出JSON
     * @Param  resultMap 数据
     * @Return void
     */
    public static void responseJson(ServletResponse response, Map<String, Object> resultMap){
        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=utf-8");
            out = response.getWriter();
            out.println(new ObjectMapper().writeValueAsString(resultMap));
        } catch (Exception e) {
            log.error("【JSON输出异常】"+e);
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }
    /**
     * 返回成功示例
     * @Param  resultMap  返回数据MAP
     * @Return Map<String,Object> 返回数据MAP
     */
    public static Map<String, Object> resultSuccess(Map<String, Object> resultMap){
        resultMap.put("message","操作成功");
        resultMap.put("code", SUCCESS);
        return resultMap;
    }
    /**
     * 返回失败示例
     * @Author Sans
     * @CreateTime 2019/9/28 11:31
     * @Param  resultMap  返回数据MAP
     * @Return Map<String,Object> 返回数据MAP
     */
    public static Map<String, Object> resultError(Map<String, Object> resultMap){
        resultMap.put("message","操作失败");
        resultMap.put("code",SERVER_ERROR);
        return resultMap;
    }
    /**
     * 通用示例
     * @Param  code 信息码
     * @Param  msg  信息
     * @Return Map<String,Object> 返回数据MAP
     */
    public static Map<String, Object> resultCode(Integer code,String msg){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message",msg);
        resultMap.put("code",code);
        return resultMap;
    }

}
