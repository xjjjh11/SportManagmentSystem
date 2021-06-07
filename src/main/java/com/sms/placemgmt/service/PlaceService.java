package com.sms.placemgmt.service;

import com.sms.placemgmt.pojo.Place;

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
}
