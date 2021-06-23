package com.sms.placemgmt.service;

import com.sms.placemgmt.pojo.Place;
import com.sms.placemgmt.pojo.TimeSlot;

import java.util.List;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/7 19:17
 */
public interface PlaceService {

    /**
     * 添加场地
     * @param place
     * @return
     */
    Integer addPlace(Place place);

    /**
     * 查询全部场地类型
     * @return
     */
    List<Integer> findPlaceTypes();

    /**
     * 查询某个类型下的所有场地
     * @param placeType
     * @return
     */
    List<Place> findAllPlaceByType(Integer placeType);

    /**
     * 查询某个时间段下某种类型的空闲场地
     * @param placeType
     * @param timeZone
     * @return
     */
    List<Place> findPlaceByTypeAndTime(Integer placeType,Integer week,String timeZone);

    /**
     * 查询某个场地的某一天的空闲时间
     * @param placeName
     * @param placeType
     * @return
     */
    List<TimeSlot> findEnableTimeByPlaceNameAndType(String placeName,Integer placeType);

    /**
     * 查询场地的本周内的使用情况
     * @param placeName
     * @param placeType
     * @return
     */
    Map<String,Object> findAllTime(String placeName,Integer placeType,Integer week);

    /**
     * 据所选场地类型和时间，查询对应的收费标准
     * @param placeType
     * @param week
     * @param timeZone
     * @return
     */
    Double findRateByPlaceTypeAndTime(Integer placeType,Integer week,String timeZone);

    /**
     * 在场地空闲时，删除场地
     * @param id
     * @return
     */
    Integer delPlaceById(Integer id,String placeName,Integer placeType);
}
