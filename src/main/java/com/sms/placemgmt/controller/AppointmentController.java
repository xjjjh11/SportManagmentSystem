package com.sms.placemgmt.controller;

import com.sms.placemgmt.common.PlaceStatus;
import com.sms.placemgmt.pojo.Appointment;
import com.sms.placemgmt.service.AppointmentService;
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
 * @date 2021/6/8 7:57
 */
@Api(tags = "预约操作接口")
@RestController
@RequestMapping("/appoint")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    Map<String,Object> map = new HashMap<>();

    @ApiOperation(value = "个人用户场地预约",notes = "场地预约，全部信息都需填写！")
    @PostMapping("/appoint")
    public Map<String,Object> appoint(@RequestParam("一卡通号") String userNumber, @RequestParam("用户名")String username,
                                           @RequestParam("手机号")String phone, @RequestParam("预约类型")Integer appointType,
                                           @RequestParam("场地类型")Integer placeType, @RequestParam("场地名称")String placeName,
                                           @RequestParam("预约周几")Integer week, @RequestParam("预约哪段时间")String timeZone){
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
       }else if (status.equals(PlaceStatus.USING)){
           map.put("title","场地正在使用中！");
           return ResultUtil.resultError(map);
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
    @ApiOperation(value = "上课或校队场地预约",notes = "需要管理员或超级管理员权限，全部信息都需填写！")
    @PostMapping("/admin/appoint")
    public Map<String,Object> appointByAdmin(@RequestParam("一卡通号") String userNumber, @RequestParam("用户名")String username,
                                           @RequestParam("手机号")String phone, @RequestParam("预约类型")Integer appointType,
                                           @RequestParam("场地类型")Integer placeType, @RequestParam("场地名称")String placeName,
                                           @RequestParam("预约周几")Integer week, @RequestParam("预约哪段时间")String timeZone){
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
        }else if (status.equals(PlaceStatus.USING)){
            map.put("title","场地正在使用中！");
            return ResultUtil.resultError(map);
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

    @ApiOperation(value = "查询全部预约信息",notes = "查询全部预约信息")
    @PostMapping("/showAll")
    public Map<String,Object> cancelAppoint(){
        List<Appointment> appointments = appointmentService.findAllAppoints();
        Map<String,Object> map = new HashMap<>();
        if (appointments != null){
            map.put("title","查询成功！");
            map.put("datas",appointments);
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","当前没有预约，请先预约！");
            return ResultUtil.resultError(map);
        }
    }

    @ApiOperation(value = "取消预约",notes = "取消预约后，并修改该场地的状态")
    @PostMapping("/cancelAppoint")
    public Map<String,Object> cancelAppoint(Integer appointId,Integer placeType,String placeName){
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

    @ApiOperation(value = "修改预约信息",notes = "修改预约信息，只能修改用户名或手机号")
    @PostMapping("/modifyAppoint")
    public Map<String,Object> modifyAppoint(String username,String phone,Integer appointId){
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
