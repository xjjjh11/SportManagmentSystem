package com.sms.contestmgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("裁判信息")
public class Judgment {
    /**
     * 裁判id
     */
    @ApiModelProperty("裁判id")
    private Integer id;

    /**
     * 裁判姓名
     */
    @ApiModelProperty("裁判姓名")
    private String name;

    /**
     * 裁判参与的赛事id
     */
    @ApiModelProperty("裁判参与赛事id")
    private Integer contestId;
}
