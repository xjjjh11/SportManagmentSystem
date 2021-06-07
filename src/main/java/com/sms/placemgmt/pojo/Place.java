package com.sms.placemgmt.pojo;

import lombok.Data;

/**
 * @author Jared
 * @date 2021/6/7 17:20
 */
@Data
public class Place {
    private Integer id;
    private Integer type;
    private String place_name;
    private Integer state;
    private Integer timeId;
}
