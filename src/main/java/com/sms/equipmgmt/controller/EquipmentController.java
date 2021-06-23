package com.sms.equipmgmt.controller;

import com.sms.equipmgmt.pojo.Equipment;
import com.sms.equipmgmt.service.EquipmentService;
import com.sms.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/10 16:09
 */
@Api(tags = "器材操作接口")
@Slf4j
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    Map<String,Object> map = new HashMap<>();

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/admin/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "器材类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "number",value = "新增器材数",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "buyRates",value = "器材购入价格",dataType = "Double",dataTypeClass = Double.class)
    })
    @ApiOperation(value = "新增器材",notes = "需要管理员或超级管理员权限才可新增器材")
    public Map<String,Object> add(@RequestParam("type") Integer type,@RequestParam("number") Integer number,
                                  @RequestParam("buyRates") Double buyRates){
        Equipment equipment = new Equipment();
        equipment.setType(type);
        equipment.setNumber(number);
        equipment.setBuyRates(buyRates);
        Integer flag = equipmentService.addEquipment(equipment);
        if (flag != null && flag == 1){
            map.put("title","新增器材成功！");
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","器材类型和数量未填写！");
            return ResultUtil.resultError(map);
        }
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/admin/repair")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "器材类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "serviceNum",value = "报修数量",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "number",value = "原本器材数量",dataType = "Integer",dataTypeClass = Integer.class)
    })
    @ApiOperation(value = "报修器材",notes = "需要管理员或超级管理员权限才可报修器材")
    public Map<String,Object> repair(@RequestParam("type") Integer type,@RequestParam("serviceNum") Integer serviceNum,
                                     @RequestParam("number") Integer number){
        Map<String,Object> map = new HashMap<>();
        Equipment equipment = equipmentService.repairEquipmentByType(type, serviceNum, number);
        if (equipment != null){
            map.put("title","报修成功！");
            map.put("datas",equipment);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","报修失败！");
        return ResultUtil.resultError(map);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/admin/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "器材类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "number",value = "更改后的器材数量",dataType = "Integer",dataTypeClass = Integer.class)
    })
    @ApiOperation(value = "增加或减少可用器材数量",notes = "需要管理员或超级管理员权限才可操作器材数量")
    public Map<String,Object> update(@RequestParam("type") Integer type,@RequestParam("number") Integer number){
        if (equipmentService.modifyEquipmentNumByType(type,number) == 1){
            map.put("title","成功更新器材数量！");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","更新器材数量失败！");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "查询全部器材信息",notes = "普通用户也可进行操作")
    public Map<String,Object> findAll(){
        Map<String,Object> map = new HashMap<>();
        List<Equipment> allEquipments = equipmentService.findAllEquipments();
        if (allEquipments != null){
            map.put("title","成功查询全部器材信息！");
            map.put("datas",allEquipments);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","当前无器材！");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/findRepair")
    @ApiOperation(value = "查询全部修理中的器材类型和数量",notes = "普通用户也可进行操作")
    public Map<String,Object> findRepair(){
        Map<String,Object> map = new HashMap<>();
        List<Equipment> repairEquipNum = equipmentService.findRepairEquipNum();
        if (repairEquipNum != null){
            map.put("title","成功查询全部维修中的器材信息！");
            map.put("datas",repairEquipNum);
        }else
            map.put("title","当前无维修中的器材！");
        return ResultUtil.resultSuccess(map);
    }

    @PostMapping("/findRates")
    @ApiImplicitParam(name = "type",value = "器材类型",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "查询某类型器材的收费标准",notes = "普通用户也可进行操作")
    public Map<String,Object> findRates(@RequestParam("type") Integer type){
        Map<String,Object> map = new HashMap<>();
        Double rates = equipmentService.findRatesByType(type);
        if (rates != null){
            map.put("title","成功查询该类型器材的收费标准！");
            map.put("data",rates);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","查询收费标准失败！");
        return ResultUtil.resultError(map);
    }
}
