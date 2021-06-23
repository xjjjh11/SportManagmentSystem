package com.sms.placemgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jared
 * @date 2021/6/7 17:28
 */
@Data
@ApiModel("预约类")
public class Appointment {
    /**
     * 预约id
     */
    @ApiModelProperty("预约id")
    private Integer id;
    /**
     * 用户账号（唯一标识用户）
     */
    @ApiModelProperty("用户账号（唯一标识用户）")
    private String userNumber;
    /**
     * 预约人姓名
     */
    @ApiModelProperty("预约人姓名")
    private String username;
    /**
     * 预约人手机号
     */
    @ApiModelProperty("预约人手机号")
    private String phone;
    /**
     * 预约类型
     */
    @ApiModelProperty("预约类型；0：个人    1：校队    2：上课    3：比赛")
    private Integer appointType;
    /**
     * 场地类型
     */
    @ApiModelProperty("场地类型；0：羽毛球   1：乒乓球   2：台球    3：篮球    4：保龄球")
    private Integer placeType;
    /**
     * 要预约的场地名称
     */
    @ApiModelProperty("场地名称")
    private String placeName;
    /**
     * 场地状态，默认为可预约
     */
    @ApiModelProperty("场地状态")
    private Integer placeStatus = 0;
    /**
     * 预约星期几
     */
    @ApiModelProperty("预约星期几")
    private Integer week;
    /**
     * 预约的时间段
     */
    @ApiModelProperty("预约的时间段")
    private String timeZone;

}
