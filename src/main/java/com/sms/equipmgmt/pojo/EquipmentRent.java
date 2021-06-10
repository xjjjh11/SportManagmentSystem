package com.sms.equipmgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jared
 * @date 2021/6/10 19:39
 */
@Data
@ApiModel("器材租借类")
public class EquipmentRent {

    /**
     * 器材租借类id
     */
    @ApiModelProperty("器材租借id")
    private Integer id;
    /**
     * 租借人用户名
     */
    @ApiModelProperty("租借人用户名")
    private String username;
    /**
     * 租借人一卡通号
     */
    @ApiModelProperty("租借人一卡通号")
    private String userNumber;
    /**
     * 租借时长
     */
    @ApiModelProperty("租借时长")
    private Integer duration;
    /**
     * 租借人手机号
     */
    @ApiModelProperty("租借人手机号")
    private String phone;
    /**
     * 租借器材的数量
     */
    @ApiModelProperty("租借器材的数量")
    private Integer rentNumber;
    /**
     * 当前租借时间
     */
    @ApiModelProperty("当前租借时间")
    private String rentTime;
    /**
     * 租借器材金额
     */
    @ApiModelProperty("租借器材金额")
    private Double rentRate;
    /**
     * 租借器材的类型
     */
    @ApiModelProperty("租借器材的类型")
    private Integer equipType;
}
