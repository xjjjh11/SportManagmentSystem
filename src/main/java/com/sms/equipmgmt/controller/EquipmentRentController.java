package com.sms.equipmgmt.controller;

import com.sms.equipmgmt.pojo.EquipmentRent;
import com.sms.equipmgmt.service.EquipmentRentService;
import com.sms.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/10 19:58
 */
@Api(tags = "器材租借接口")
@RestController
@RequestMapping("/rentEquip")
public class EquipmentRentController {
    Map<String,Object> map = new HashMap<>();
    @Autowired
    private EquipmentRentService equipmentRentService;

    @ApiOperation(value = "租借器材",notes = "全部信息为必填项")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class),
        @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",dataTypeClass = String.class),
        @ApiImplicitParam(name = "duration",value = "租借时长",dataType = "Integer",dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",dataTypeClass = String.class),
        @ApiImplicitParam(name = "rentNumber",value = "租借数量",dataType = "Integer",dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "equipType",value = "租借类型",dataType = "Integer",dataTypeClass = Integer.class),
    })
    @PostMapping("/rent")
    public Map<String,Object> rent(@RequestParam("userNumber") String userNumber,@RequestParam("username") String username,
                                   @RequestParam("duration") Integer duration, @RequestParam("phone") String phone,
                                   @RequestParam("rentNumber") Integer rentNumber, @RequestParam("equipType") Integer equipType){
        // 组装数据
        EquipmentRent equipmentRent = new EquipmentRent();
        equipmentRent.setUserNumber(userNumber);
        equipmentRent.setUsername(username);
        equipmentRent.setDuration(duration);
        equipmentRent.setPhone(phone);
        equipmentRent.setRentNumber(rentNumber);
        equipmentRent.setEquipType(equipType);
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        System.out.println(currentTime);
        equipmentRent.setRentTime(currentTime);
        Integer flag = equipmentRentService.rentEquipment(equipmentRent);
        if (flag == 1){
            map.put("title","成功租借器材，请按时归还！");
            return ResultUtil.resultSuccess(map);
        }else if (flag == -1){
            map.put("title","器材不足！");
            return ResultUtil.resultError(map);
        }
        map.put("title","租借失败！");
        return ResultUtil.resultError(map);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "租借器材id",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "eType",value = "器材类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class),
    })
    @ApiOperation(value = "归还器材",notes = "管理员以上的权限才可进行操作")
    public Map<String,Object> delete(@RequestParam("id") Integer id, @RequestParam("eType") Integer eType,
                                     @RequestParam("userNumber") String userNumber){

        Integer flag = equipmentRentService.delEquipmentRent(id, eType, userNumber);
        if (flag == 1){
            map.put("title","器材归还成功！");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","器材归还失败！");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/find")
    @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class)
    @ApiOperation(value = "查询用户租借信息",notes = "根据用户一卡通号，查询出用户全部的租借信息")
    public Map<String,Object> find(@RequestParam("userNumber") String userNumber){
        Map<String,Object> map = new HashMap<>();
        List<EquipmentRent> rents = equipmentRentService.findRentEquipByUserNum(userNumber);
        if (rents != null){
            map.put("title","用户全部租借信息");
            map.put("datas",rents);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","用户全部租借信息查询失败！");
        return ResultUtil.resultError(map);
    }

    @ApiOperation(value = "查询全部租借信息",notes = "查询系统中全部租借信息")
    @PostMapping("/findAll")
    public Map<String,Object> findAll(){
        Map<String,Object> map = new HashMap<>();
        List<EquipmentRent> rents = equipmentRentService.findAllRentEquip();
        if (rents != null){
            map.put("title","全部租借信息查询成功");
            map.put("datas",rents);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","全部租借信息查询失败！");
        return ResultUtil.resultError(map);
    }
}
