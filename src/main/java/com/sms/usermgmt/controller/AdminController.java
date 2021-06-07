package com.sms.usermgmt.controller;

import com.sms.usermgmt.pojo.User;
import com.sms.usermgmt.service.AdminService;
import com.sms.usermgmt.util.ResultUtil;
import com.sms.usermgmt.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/5 16:29
 */
@Api(tags = "管理员相关操作接口，拥有管理权限才可进行操作")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    private Map<String,Object> map = new HashMap<>();
    private List<User> allUserInfo = new ArrayList<>();

    @PostMapping(value = "/showAll")
    @ApiOperation(value = "查询全部用户信息",notes = "查询全部用户信息，并以数组的形式返回值",
            httpMethod = "POST",responseContainer = "查询成功，查询失败")
    public Map<String,Object> showAllUserInfo(){
        allUserInfo = adminService.showAllUserInfo();
        if (allUserInfo != null){
            map.put("title","全部用户数据");
            map.put("datas",allUserInfo);
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","当前没有任何用户！");
            return ResultUtil.resultError(map);
        }
    }

    @PostMapping(value = "/showRoleForUser")
    @ApiOperation(value = "查询全部普通用户",notes = "查询全部普通用户信息，即除管理员和超级管理员外的全部用户",
            httpMethod = "POST",responseContainer = "查询成功，查询失败")
    public Map<String,Object> showUserRoleForUser(){
        allUserInfo = adminService.showUserRoleForUser();
        if (allUserInfo != null){
            map.put("title","普通用户数据");
            map.put("datas",allUserInfo);
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","当前没有任何用户！");
            return ResultUtil.resultError(map);
        }
    }

    @PostMapping(value = "/showRoleForAdmin")
    @ApiOperation(value = "查询全部管理员",notes = "查询全部管理员信息，即除普通用户外的全部用户",
            httpMethod = "POST",responseContainer = "查询成功，查询失败")
    public Map<String,Object> showUserRoleForAdmin(){
        allUserInfo = adminService.showUserRoleForAdmin();
        if (allUserInfo != null){
            map.put("title","普通用户数据");
            map.put("datas",allUserInfo);
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","当前没有任何用户！");
            return ResultUtil.resultError(map);
        }
    }

}
