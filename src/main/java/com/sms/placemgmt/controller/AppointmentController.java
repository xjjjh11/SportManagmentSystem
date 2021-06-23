package com.sms.placemgmt.controller;

import com.sms.placemgmt.common.PlaceStatus;
import com.sms.placemgmt.pojo.Appointment;
import com.sms.placemgmt.service.AppointmentService;
import com.sms.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @date 2021/6/8 7:57
 */
@Api(tags = "场地预约操作接口")
@RestController
@RequestMapping("/appoint")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    Map<String,Object> map = new HashMap<>();

    @ApiOperation(value = "个人用户场地预约",notes = "场地预约，全部信息都需填写！")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "appointType",value = "预约类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "placeType",value = "场地类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "placeName",value = "场地名称",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "week",value = "预约周几",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "timeZone",value = "预约哪段时间",dataType = "String",dataTypeClass = String.class)
    })
    @PostMapping("/appoint")
    public Map<String,Object> appoint(@RequestParam("userNumber")String userNumber,@RequestParam("username")String username,
                                      @RequestParam("phone") String phone,@RequestParam("appointType") Integer appointType,
                                      @RequestParam("placeType")Integer placeType,@RequestParam("placeName")String placeName,
                                      @RequestParam("week") Integer week,@RequestParam("timeZone") String timeZone){
        // 组装数据
        Appointment appointment = new Appointment();
        appointment.setUserNumber(userNumber);
        appointment.setUsername(username);
        appointment.setPhone(phone);
        appointment.setAppointType(appointType);
        appointment.setPlaceType(placeType);
        appointment.setPlaceName(placeName);
        appointment.setWeek(week);
        appointment.setTimeZone(timeZone);
        // 判断当前场地状态，是否预约成功
        Integer status = appointmentService.orderPlace(appointment);
       if (status.equals(PlaceStatus.APPOINT_SUCCESS)){
           map.put("title","预约成功！");
           return ResultUtil.resultSuccess(map);
       }else if (status.equals(PlaceStatus.ORDERED)){
           map.put("title","场地已被预约！");
           return ResultUtil.resultError(map);
       }else if (status.equals(PlaceStatus.CLASSING)){
           map.put("title","老师上课中！");
           return ResultUtil.resultError(map);
       }else if (status.equals(PlaceStatus.TRAIN)){
           map.put("title","校队训练中！");
           return ResultUtil.resultError(map);
       }else if (status.equals(PlaceStatus.CONTEST)){
           map.put("title","比赛使用中！");
           return ResultUtil.resultError(map);
       }else {
           map.put("title","未知原因，预约失败！");
           return ResultUtil.resultError(map);
       }
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/admin/appoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminNumber",value = "管理员账号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "adminName",value = "管理员名",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "appointType",value = "预约类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "placeType",value = "场地类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "placeName",value = "场地名称",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "week",value = "预约周几",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "timeZone",value = "预约哪段时间",dataType = "String",dataTypeClass = String.class)
    })
    @ApiOperation(value = "上课或校队场地预约",notes = "需要管理员或超级管理员权限，全部信息都需填写！")
    public Map<String,Object> appointByAdmin(@RequestParam("adminNumber") String adminNumber,@RequestParam("adminName") String adminName,
                                             @RequestParam("phone") String phone,@RequestParam("appointType") Integer appointType,
                                             @RequestParam("placeType")Integer placeType,@RequestParam("placeName")String placeName,
                                             @RequestParam("week") Integer week,@RequestParam("timeZone") String timeZone){
        // 组装数据
        Appointment appointment = new Appointment();
        appointment.setUserNumber(adminNumber);
        appointment.setUsername(adminName);
        appointment.setPhone(phone);
        appointment.setAppointType(appointType);
        appointment.setPlaceType(placeType);
        appointment.setPlaceName(placeName);
        appointment.setWeek(week);
        appointment.setTimeZone(timeZone);
        // 判断当前场地状态，是否预约成功
        Integer status = appointmentService.orderPlace(appointment);
        if (status.equals(PlaceStatus.APPOINT_SUCCESS)){
            map.put("title","预约成功！");
            return ResultUtil.resultSuccess(map);
        }else if (status.equals(PlaceStatus.ORDERED)){
            map.put("title","场地已被预约！");
            return ResultUtil.resultError(map);
        }else if (status.equals(PlaceStatus.CLASSING)){
            map.put("title","老师上课中！");
            return ResultUtil.resultError(map);
        }else if (status.equals(PlaceStatus.TRAIN)){
            map.put("title","校队训练中！");
            return ResultUtil.resultError(map);
        }else if (status.equals(PlaceStatus.CONTEST)){
            map.put("title","比赛使用中！");
            return ResultUtil.resultError(map);
        }else {
            map.put("title","未知原因，预约失败！");
            return ResultUtil.resultError(map);
        }
    }

    @PostMapping("/showInfo")
    @ApiImplicitParam(name = "userNumber",value = "一卡通号",dataType = "String",dataTypeClass = String.class)
    @ApiOperation(value = "查询我的预约",notes = "根据一卡通号查询自己的场地预约情况")
    public Map<String,Object> showInfo(@RequestParam("userNumber")String userNumber){
        List<Appointment> appointments = appointmentService.findMyAppoints(userNumber);
        Map<String,Object> map = new HashMap<>();
        if (appointments.size() != 0){
            map.put("title","查询成功！");
            map.put("datas",appointments);
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","当前没有预约，请先预约！");
            return ResultUtil.resultError(map);
        }
    }

    @PostMapping("/cancelAppoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointId",value = "场地预约id",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "placeType",value = "场地类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "placeName",value = "场地名称",dataType = "String",dataTypeClass = String.class)
    })
    @ApiOperation(value = "取消预约",notes = "取消预约后，并修改该场地的状态")
    public Map<String,Object> cancelAppoint(@RequestParam("appointId")Integer appointId,@RequestParam("placeType")Integer placeType,
                                            @RequestParam("placeName")String placeName){
        Integer flag = appointmentService.cancelAppoint(appointId,placeType,placeName);
        if (flag == 1) {
            map.put("title", "已取消预约！");
            return ResultUtil.resultSuccess(map);
        }
        else {
            map.put("title","取消预约失败！");
            return ResultUtil.resultError(map);
        }
    }

    @PostMapping("/modifyAppoint")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointId",value = "场地预约id",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "username",value = "用户名",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",dataTypeClass = String.class)
    })
    @ApiOperation(value = "修改预约信息",notes = "修改预约信息，只能修改用户名或手机号")
    public Map<String,Object> modifyAppoint(@RequestParam("username")String username,@RequestParam("phone")String phone,
                                            @RequestParam("appointId")Integer appointId){
        Integer flag = appointmentService.modifyAppoint(username, phone, appointId);
        if (flag == -1){
            map.put("title","用户名或密码为空!");
            return ResultUtil.resultError(map);
        }else if (flag == 0){
            map.put("title","修改失败！");
            return ResultUtil.resultError(map);
        }else {
            map.put("title","修改成功！");
            return ResultUtil.resultSuccess(map);
        }
    }
}
