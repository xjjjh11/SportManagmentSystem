package com.sms.usermgmt.security.handler;

import com.sms.util.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登出成功处理类
 * @author Jared
 * @date 2021/6/3 15:24
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    /**
     * 用户登出返回结果
     * 这里应该让前端清除掉Token
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "登出成功");
        // 清除Security中上下文信息，即用户信息等
        SecurityContextHolder.clearContext();
        ResultUtil.responseJson(response, ResultUtil.resultSuccess(resultData));
    }
}
