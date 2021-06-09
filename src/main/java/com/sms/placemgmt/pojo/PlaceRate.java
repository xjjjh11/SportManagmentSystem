package com.sms.placemgmt.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Jared
 * @date 2021/6/7 17:38
 */
@Data
@ApiModel("场地收费标准")
public class PlaceRate {

    /**
     * 唯一标识
     */
    private Integer id;
    /**
     * 时间段
     */
    private Integer timeId;
    /**
     * 场地
     */
    private Integer placeId;
    /**
     * 收费标准
     */
    private Double rates;

}
