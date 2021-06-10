package com.sms.equipmgmt.controller;

import com.sms.equipmgmt.pojo.EquipmentRent;
import com.sms.equipmgmt.service.EquipmentRentService;
import com.sms.usermgmt.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/10 19:58
 */
@Api(tags = "器材预约操作接口")
@RestController
@RequestMapping("/rentEquip")
public class EquipmentRentController {
    Map<String,Object> map = new HashMap<>();
    @Autowired
    private EquipmentRentService equipmentRentService;

    @ApiOperation(value = "租借器材",notes = "全部信息为必填项")
    @PostMapping("/rent")
    public Map<String,Object> rent(EquipmentRent equipmentRent,@RequestParam("器材原有数量") Integer number){
        if (equipmentRentService.rentEquipment(equipmentRent,number) == 1){
            map.put("title","成功租借器材，请按时归还！");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","租借失败！");
        return ResultUtil.resultError(map);
    }

    @PreAuthorize("hasRole('admin')")
    @ApiOperation(value = "归还器材",notes = "管理员以上的权限才可进行操作")
    @PostMapping("/delete")
    public Map<String,Object> delete(@RequestParam("租借器材id") Integer id,@RequestParam("器材类型") Integer eType,
                                     @RequestParam("租借的器材数") Integer rentNum,@RequestParam("器材数")Integer number){

        Integer flag = equipmentRentService.delEquipmentRent(id, eType, rentNum, number);
        if (flag == 1){
            map.put("title","器材归还成功！");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","器材归还失败！");
        return ResultUtil.resultError(map);
    }

    @ApiOperation(value = "查询用户租借信息",notes = "根据用户一卡通号，查询出用户全部的租借信息")
    @PostMapping("/find")
    public Map<String,Object> find(@RequestParam("一卡通号") String userNumber){
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
