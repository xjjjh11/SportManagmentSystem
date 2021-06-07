package com.sms.usermgmt.controller;

import com.sms.usermgmt.common.RoleCode;
import com.sms.usermgmt.service.SuperAdminService;
import com.sms.usermgmt.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/7 11:38
 */
@Api(tags = "超级管理员相关操作接口，只有超级管理员权限才可实现")
@RestController
@RequestMapping("/superAdmin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;
    Map<String,Object> map = new HashMap<>();

    @ApiOperation(value = "添加管理员",notes = "在已有用户中，超级管理员选择添加为管理员",httpMethod = "POST")
    @PostMapping(value = "/modifyUserRole")
    public Map<String,Object> modifyRole2Admin(@RequestParam("一卡通号") String userNumber){
        if (userNumber != null){
            if (superAdminService.updUserRole2Admin(userNumber, RoleCode.ROLE_ADMIN) == 1){
                map.put("title","成功添加管理员！");
                return ResultUtil.resultSuccess(map);
            }
            map.put("title","添加管理员错误！");
            return ResultUtil.resultError(map);
        }else {
            map.put("title","用户账号为空！");
            return ResultUtil.resultError(map);
        }
    }

    @ApiOperation(value = "删除管理员",notes = "在全部管理员中，超级管理员可以删除管理员",httpMethod = "POST")
    @PostMapping(value = "/modifyAdminRole")
    public Map<String,Object> modifyRole2User(@RequestParam("一卡通号") String userNumber){
        if (userNumber != null){
            if (superAdminService.updAdminRole2User(userNumber, RoleCode.ROLE_USER) == 1){
                map.put("title","成功删除管理员！");
                return ResultUtil.resultSuccess(map);
            }
            map.put("title","删除管理员错误！");
            return ResultUtil.resultError(map);
        }else {
            map.put("title","用户账号为空！");
            return ResultUtil.resultError(map);
        }
    }
}
