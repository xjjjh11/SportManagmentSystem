package com.sms.placemgmt.controller;

import com.sms.placemgmt.pojo.Place;
import com.sms.placemgmt.service.PlaceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/7 19:29
 */
@Api(tags = "场地添加和删除（超级管理员）")
@Slf4j
@RestController
@RequestMapping("/superAdmin")
public class PlaceController {

    @Autowired
    private PlaceService placeService;
    Map<String,Object> map = new HashMap<>();

    @PostMapping("addPlace")
    public Map<String,Object> addPlace(Place place){
        if (placeService.addPlace(place) == 1){
            return map;
        }
        return map;

    }
}
