package com.sms.usermgmt.controller;

import com.sms.usermgmt.pojo.User;
import com.sms.usermgmt.service.UserService;
import com.sms.usermgmt.util.ResultUtil;
import com.sms.usermgmt.util.SecurityUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/5 16:29
 */
@Api(tags = "用户相关接口")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 以Map形式返回JSON数据
     */
    private Map<String ,Object> map = new HashMap<>();

    /**
     * 用户注册
     * @param userNumber
     * @param username
     * @param password
     * @param gender
     * @param phone
     * @param academy
     * @param major
     * @param classes
     * @return
     */
    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册",notes = "注册接口",httpMethod = "POST")
    public Map<String,Object> register(@RequestParam("一卡通号")String userNumber,@RequestParam("用户名")String username,
                              @RequestParam("密码")String password,@RequestParam("性别（0：女，1：男）")Integer gender,
                              @RequestParam("手机号")String phone,@RequestParam("学院")String academy,
                              @RequestParam("专业")String major,@RequestParam("班级")String classes){
        // 获取用户信息
//        String userNumber = req.getParameter("userNumber"); // 一卡通号或教职工账号
//        String username = req.getParameter("username");     // 用户名
//        String password = req.getParameter("password");     // 密码
//        String gender = req.getParameter("gender");         // 性别
//        String phone = req.getParameter("phone");           // 手机号
//        String academy = req.getParameter("academy");       // 学院
//        String major = req.getParameter("major");           // 专业
//        String classes = req.getParameter("classes");       // 班级
        int enabled = 0;                                       // 账号可用
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());// 账号创建时间
        User user = new User();
        // 组装用户数据
        user.setUserNumber(userNumber);
        user.setUsername(username);
        user.setPassword(password);
        user.setGender(gender);
        user.setPhone(phone);
        user.setAcademy(academy);
        user.setMajor(major);
        user.setClasses(classes);
        user.setEnabled(enabled);
        user.setCreateTime(createTime);
        log.info("user: "+user);
        // 用户信息注册到数据库中，成功注册 status==1
        Integer status = userService.insUser(user);
        if (status.equals(ResultUtil.USER_INFO_NULL))
            return ResultUtil.resultCode(ResultUtil.USER_INFO_NULL,"用户账号必填信息为空!");
        else if (status.equals(ResultUtil.USER_NUMBER_ERROR))
            return ResultUtil.resultCode(ResultUtil.USER_NUMBER_ERROR,"一卡通账号输入错误！");
        else if (status.equals(ResultUtil.PASSWORD_ERROR))
            return ResultUtil.resultCode(ResultUtil.PASSWORD_ERROR,"密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线！");
        else if (status.equals(ResultUtil.PHONE_NUMBER_ERROR))
            return ResultUtil.resultCode(ResultUtil.PHONE_NUMBER_ERROR,"手机号有误！");
        else if (status.equals(ResultUtil.USER_EXISTED))
            return ResultUtil.resultCode(ResultUtil.USER_EXISTED,"该用户已存在，请重新输入一卡通号！");
        map.put("title","用户注册成功");
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 查询全部用户信息
     * @param authentication
     * @return
     */
    @PostMapping(value = "/find")
    @ApiOperation(value = "查询用户个人信息",notes = "根据用户账号查询，前提是已经登录",httpMethod = "POST")
    public Map<String,Object> showUser(Authentication authentication){
        User userInfo = (User) authentication.getPrincipal();
        log.info("userNumber:"+userInfo.getUserNumber());
        if (userInfo.getUserNumber() != null) {
            User user = userService.selUserByUserNumber(userInfo.getUserNumber());
            map.put("title","用户查询成功");
            map.put("data",user);
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","用户查询失败");
            return ResultUtil.resultError(map);
        }
    }

    /**
     * 修改密码
     * @param userNumber
     * @param pwd 新密码
     * @param newPwd 重复输入的密码
     * @return
     */
    @PostMapping(value = "/modifyPwd")
    @ApiOperation(value = "修改密码",notes = "用户修改密码，输入两次密码，两次密码一致才能修改成功",httpMethod = "POST")
    public Map<String,Object> modifyPwd(@RequestParam("一卡通号") String userNumber, @RequestParam("新密码") String pwd,
                                       @RequestParam("再次输入密码") String newPwd){
        if (pwd.equals(newPwd)){
            Integer status = userService.updPwdByUserNumber(userNumber, newPwd);
            if (status.equals(ResultUtil.PASSWORD_ERROR))
                return ResultUtil.resultCode(ResultUtil.PASSWORD_ERROR,"密码必须以字母开头，长度在6~18之间，只能包含字母、数字和下划线！");
            else if (status.equals(ResultUtil.USER_NUMBER_ERROR))
                return ResultUtil.resultCode(ResultUtil.USER_NUMBER_ERROR,"用户账号为空！");
            // 成功修改密码
            else if (status.equals(1)){
                map.put("title","成功修改密码！");
                return ResultUtil.resultSuccess(map);
            }
        }else {
            map.put("title","两次输入的密码不一致");
            return ResultUtil.resultError(map);
        }
        map.put("title","修改密码失败");
        return ResultUtil.resultError(map);
    }

    /**
     * 修改个人信息
     * @param userNumber 一卡通号
     * @param gender 性别
     * @param academy 学院
     * @param major 专业
     * @param classes 班级
     * @param phone 手机号
     * @return
     */
    @PostMapping(value = "/modifyUserInfo")
    @ApiOperation(value = "修改个人信息",notes = "用户修改个人信息",httpMethod = "POST")
    public Map<String,Object> modifyUserInfo(@RequestParam("一卡通号") String userNumber, @RequestParam("性别") Integer gender,
                                             @RequestParam("学院") String academy,@RequestParam("专业") String major,
                                             @RequestParam("班级") String classes,@RequestParam("手机号") String phone){
        Integer status = userService.updUserInfoByUserNumber(userNumber, gender, academy, major, classes, phone);
        if (status.equals(ResultUtil.USER_NUMBER_ERROR))
            return ResultUtil.resultCode(ResultUtil.USER_NUMBER_ERROR,"用户账号为空！");
        else if (status.equals(ResultUtil.PHONE_NUMBER_ERROR))
            return ResultUtil.resultCode(ResultUtil.PHONE_NUMBER_ERROR,"手机号输入有误！");
        else {
            map.put("title","修改成功！");
            return ResultUtil.resultSuccess(map);
        }
    }
}
