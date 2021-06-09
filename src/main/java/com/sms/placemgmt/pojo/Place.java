package com.sms.placemgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jared
 * @date 2021/6/7 17:20
 */
@Data
@ApiModel("场地信息")
public class Place {
    /**
     * 场地id
     */
    @ApiModelProperty("场地id")
    private Integer id;
    /**
     * 场地类型：
     *      0:羽毛球场 1:乒乓球场 2:台球场 3:篮球场 4:保龄球场
     */
    @ApiModelProperty("场地类型")
    private Integer type;
    /**
     * 场地名称
     *      如：篮球场A
     */
    @ApiModelProperty("场地名字")
    private String placeName;
    /**
     * 场地状态该时间段该场地的状态：
     *      0：空闲 1：使用中 2：被预约 3：上课 4：校队 5：比赛
     * （默认为0，空闲状态）
     */
    @ApiModelProperty("场地状态")
    private Integer status;
    /**
     * 时间段id
     *      即可用时间段
     */
    @ApiModelProperty("场地可用时间段")
    private Integer timeId;
}
