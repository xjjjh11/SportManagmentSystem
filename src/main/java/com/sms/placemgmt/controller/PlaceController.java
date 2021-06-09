package com.sms.placemgmt.controller;

import com.sms.placemgmt.common.PlaceStatus;
import com.sms.placemgmt.pojo.Appointment;
import com.sms.placemgmt.pojo.Place;
import com.sms.placemgmt.pojo.TimeSlot;
import com.sms.placemgmt.service.PlaceService;
import com.sms.usermgmt.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2021/6/7 19:29
 */
@Api(tags = "场地操作接口")
@Slf4j
@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;
    Map<String,Object> map = new HashMap<>();

    @PreAuthorize("hasRole('superAdmin')")
    @PostMapping("/superAdmin/addPlace")
    @ApiOperation(value = "添加场地",notes = "超级管理员登录后才能添加场地")
    public Map<String,Object> addPlace(@RequestParam("场地类型") Integer placeType,
                                       @RequestParam("场地名称") String placeName){
        Place place = new Place();
        place.setPlaceName(placeName);
        place.setType(placeType);
        place.setStatus(PlaceStatus.FREE);
        if (placeService.addPlace(place) == 1){
            map.put("title","成功添加场地！");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title","添加场地失败！");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryTypes")
    @ApiOperation(value = "查询全部场地类型",notes = "查询全部场地类型")
    public Map<String,Object> showAllTypes(){
        List<Integer> placeTypes = placeService.findPlaceTypes();
        if (placeTypes != null) {
            map.put("title", "查询成功！");
            map.put("datas", placeTypes);
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","当前没有场地，请先添加！");
            return ResultUtil.resultError(map);
        }
    }

    @PostMapping("/queryByType")
    @ApiOperation(value = "查询某个类型下的所有场地",notes = "查询某个类型下的所有场地")
    public Map<String,Object> showPlaces(@RequestParam("场地类型") Integer placeType){
        List<Place> places = placeService.findAllPlaceByType(placeType);
        if (places != null){
            map.put("title","查询成功！");
            map.put("datas",places);
            return ResultUtil.resultSuccess(map);
        }else {
            map.put("title","查询失败！");
            return ResultUtil.resultError(map);
        }
    }

    @PostMapping("/queryFreePlace")
    @ApiOperation(value = "查询某个时间段下某种类型的空闲场地",notes = "根据用户选择的时间段和场地类型返回场地信息")
    public Map<String,Object> showFreePlaces(@RequestParam("场地类型") Integer placeType,
                                             @RequestParam("星期几") Integer week,@RequestParam("时间段")String timeZone){
        List<Place> places = placeService.findPlaceByTypeAndTime(placeType, week, timeZone);
        if (places != null){
            map.put("title","查询成功！");
            map.put("datas",places);
            return ResultUtil.resultSuccess(map);
        }else
            map.put("title","当前无空闲场地！");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryEnableTime")
    @ApiOperation(value = "查询某个场地的一周内的空闲时间段",notes = "根据用户选择的场地名称和类型，返回场地空闲时间信息")
    public Map<String,Object> showEnableTime(@RequestParam("场地名称") String placeName,@RequestParam("场地类型") Integer placeType){

        List<TimeSlot> timeSlots = placeService.findEnableTimeByPlaceNameAndType(placeName, placeType);
        if (timeSlots != null){
            map.put("title","查询成功！");
            map.put("datas",timeSlots);
            return ResultUtil.resultSuccess(map);
        }else
            map.put("title","当前无空闲时间！");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryAllTime")
    @ApiOperation(value = "查询某个场地的所有时间段情况",notes = "查询某个场地的所有时间段情况")
    public Map<String,Object> showAllTime(@RequestParam("场地名称") String placeName,@RequestParam("场地类型") Integer placeType){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> allTime = placeService.findAllTime(placeName, placeType);
        List<TimeSlot> enabledTime = (List<TimeSlot>) allTime.get("enabledTime");
        List<Appointment> occupyTime = (List<Appointment>) allTime.get("occupyTime");
        if (occupyTime == null){
            map.put("title","当前无被预约时间！");
            map.put("enabledTime",enabledTime);
            map.put("occupyTime", null);
        }else {
            map.put("title","查询成功！");
            map.put("enabledTime",enabledTime);
            map.put("occupyTime",occupyTime);
        }
        return ResultUtil.resultSuccess(map);
    }

    @PostMapping("/queryRate")
    @ApiOperation(value = "查询对应的收费标准",notes = "根据用户选择的场类型和时间段，返回场地收费标准")
    public Map<String,Object> showRate(@RequestParam("场地类型") Integer placeType,
                                       @RequestParam("星期几") Integer week,@RequestParam("时间段") String timeZone){
        Integer rate = placeService.findRateByPlaceTypeAndTime(placeType, week, timeZone);
        if (rate == 0){
            map.put("title","查询成功，该场地免费试用！");
            map.put("data",rate);
        }else if (rate > 0){
            map.put("title","查询成功，收费标准如下：");
            map.put("data",rate);
        }else {
            map.put("title","查询失败！");
            return ResultUtil.resultError(map);
        }
        return ResultUtil.resultSuccess(map);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除场地",notes = "场地空闲时，才能被删除")
    public Map<String,Object> showRate(@RequestParam("场地id") Integer id, @RequestParam("场地名字") String placeName,
                                       @RequestParam("场地类型") Integer placeType){
        Integer flag = placeService.delPlaceById(id, placeName, placeType);
        if (flag == 1){
            map.put("title","删除成功！");
            return ResultUtil.resultSuccess(map);
        }else if (flag == -1){
            map.put("title","该场地已被预约，无法删除！");
            return ResultUtil.resultError(map);
        }else {
            map.put("title","删除失败！");
            return ResultUtil.resultError(map);
        }
    }
}
