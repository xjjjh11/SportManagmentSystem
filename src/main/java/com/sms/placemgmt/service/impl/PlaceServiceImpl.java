package com.sms.placemgmt.service.impl;

import com.sms.placemgmt.mapper.PlaceMapper;
import com.sms.placemgmt.pojo.Place;
import com.sms.placemgmt.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jared
 * @date 2021/6/7 19:17
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceMapper placeMapper;

    @Override
    public Integer addPlace(Place place) {
        return placeMapper.insPlace(place);
    }
}
