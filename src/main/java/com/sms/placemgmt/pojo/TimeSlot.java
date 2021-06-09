package com.sms.placemgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jared
 * @date 2021/6/7 17:35
 */
@Data
@ApiModel("时间段")
public class TimeSlot {
    /**
     * 时间段id，唯一标识
     */
    @ApiModelProperty("时间段id，唯一标识")
    private Integer id;
    /**
     * 星期
     */
    @ApiModelProperty("星期几")
    private Integer week;
    /**
     * 时间段
     */
    @ApiModelProperty("时间段，如：8:00~10：00")
    private String timeZone;
    /**
     * 时长
     */
    @ApiModelProperty("时长")
    private Integer timeLong;
}
