package com.sms.usermgmt.controller;

import com.sms.usermgmt.pojo.User;
import com.sms.usermgmt.service.UserService;
import com.sms.util.ResultUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 以Map形式返回JSON数据
     */
    private Map<String ,Object> map = new HashMap<>();

    /**
     * 用户注册
     * @return
     */
    @PostMapping(value = "/register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "gender",value = "性别（0：女，1：男）",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "academy",value = "学院",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "major",value = "专业",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "classes",value = "班级",dataType = "String",dataTypeClass = String.class)
    })
    @ApiOperation(value = "用户注册",notes = "注册接口",httpMethod = "POST")
    public Map<String,Object> register(@RequestParam("userNumber") String userNumber,@RequestParam("username") String username,
                                       @RequestParam("password") String password,@RequestParam("gender") Integer gender,
                                       @RequestParam("phone") String phone,@RequestParam("academy") String academy,
                                       @RequestParam("major") String major,@RequestParam("classes") String classes){

        int enabled = 1; // 账号可用
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
     * 教职工注册
     * @return
     */
    @PostMapping(value = "/regTeacher")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherNumber",value = "教职工号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "gender",value = "性别（0：女，1：男）",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "academy",value = "学院",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "major",value = "专业",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "classes",value = "班级",dataType = "String",dataTypeClass = String.class)
    })
    @ApiOperation(value = "教职工注册",notes = "注册接口",httpMethod = "POST")
    public Map<String,Object> registerTeacher(@RequestParam("teacherNumber") String teacherNumber,@RequestParam("username")String username,
                                       @RequestParam("password") String password,@RequestParam("gender") Integer gender,
                                       @RequestParam("phone") String phone,@RequestParam("academy") String academy,
                                       @RequestParam(value = "major",required = false) String major,
                                       @RequestParam(value = "classes",required = false) String classes){

        Map<String, Object> map = new HashMap<>();
        int enabled = 1; // 账号可用
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());// 账号创建时间
        User user = new User();
        // 组装用户数据
        user.setUserNumber(teacherNumber);
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
     * @param userNumber
     * @return
     */
    @PostMapping(value = "/find")
    @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class)
    @ApiOperation(value = "查询用户个人信息",notes = "根据用户账号查询，前提是已经登录",httpMethod = "POST")
    public Map<String,Object> showUser(@RequestParam("userNumber") String userNumber){
        if (userNumber != null) {
            User user = userService.selUserByUserNumber(userNumber);
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
     * @param newPwd 新密码
     * @param reNewPwd 重复输入的密码
     * @return
     */
    @PostMapping(value = "/modifyPwd")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "pwd",value = "原来的密码",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "newPwd",value = "新密码",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "reNewPwd",value = "再次输入新密码",dataType = "String",dataTypeClass = String.class)
    })
    @ApiOperation(value = "修改密码",notes = "用户修改密码，输入两次密码，两次密码一致才能修改成功",httpMethod = "POST")
    public Map<String,Object> modifyPwd(@RequestParam("userNumber") String userNumber,@RequestParam("pwd") String pwd,
                                        @RequestParam("newPwd") String newPwd,@RequestParam("reNewPwd") String reNewPwd){
        String selPwd = userService.findPwdByUserNumber(userNumber);
        if (bCryptPasswordEncoder.matches(pwd,selPwd)){
            if (newPwd.equals(reNewPwd)){
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
        }else {
            map.put("title","密码输入错误，请重新输入！");
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "gender",value = "性别（0：女，1：男）",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "academy",value = "学院",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "major",value = "专业",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "classes",value = "班级",dataType = "String",dataTypeClass = String.class)
    })
    @ApiOperation(value = "修改个人信息",notes = "用户修改个人信息",httpMethod = "POST")
    public Map<String,Object> modifyUserInfo(@RequestParam("userNumber") String userNumber,@RequestParam("username") String username,
                                             @RequestParam("gender") Integer gender, @RequestParam("phone") String phone,
                                             @RequestParam("academy") String academy, @RequestParam("major") String major,
                                             @RequestParam("classes") String classes){
        Integer status = userService.updUserInfoByUserNumber(userNumber,username, gender, academy, major, classes, phone);
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
