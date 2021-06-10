package com.sms.equipmgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jared
 * @date 2021/6/10 15:37
 */
@Data
@ApiModel("器材类")
public class Equipment {
    /**
     * 器材类型
     *  0:羽毛球拍  1:毽子  2:排球  3:篮球
     *      4:足球  5:乒乓球拍  6:保龄球
     */
    @ApiModelProperty("器材类型")
    private Integer type;
    /**
     * 器材数量
     */
    @ApiModelProperty("器材数量")
    private Integer number;
    /**
     * 器材被租借数量
     */
    @ApiModelProperty("器材被租借数量")
    private Integer rentNum;
    /**
     * 器材被维修数量
     */
    @ApiModelProperty("器材被维修数量")
    private Integer serviceNum;
    /**
     * 器材收费标准
     *      1元/h
     */
    @ApiModelProperty("器材收费标准")
    private Double rates;
}
