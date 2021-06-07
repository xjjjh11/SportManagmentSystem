package com.sms.usermgmt.security.handler;

import com.sms.usermgmt.pojo.User;
import com.sms.usermgmt.util.JWTCommon;
import com.sms.usermgmt.util.JwtTokenUtil;
import com.sms.usermgmt.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理类
 * @author Jared
 * @date 2021/6/3 15:20
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 登录成功处理类
     * @param request
     * @param response
     * @param authentication 认证信息 包括用户信息 密码 权限等
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 组装JWT
        User user =  (User) authentication.getPrincipal();
        log.info("(User)authentication.getPrincipal(): "+user);
        String token = JwtTokenUtil.createAccessToken(user);
        token = JWTCommon.TOKEN_PREFIX + token;
        // 封装返回参数
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "登录成功");
        resultData.put("token",token);
        ResultUtil.responseJson(response,resultData);
    }
}
