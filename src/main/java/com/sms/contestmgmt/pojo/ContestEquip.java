package com.sms.contestmgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("赛事使用器材信息")
public class ContestEquip {
    /**
     * 赛事使用器材记录id
     */
    @ApiModelProperty("赛事使用器材记录id")
    private Integer id;

    /**
     * 使用该器材赛事id
     */
    @ApiModelProperty("使用该器材赛事id")
    private Integer contestId;

    /**
     * 使用该器材的id
     */
    @ApiModelProperty("器材id")
    private Integer equipId;

    /**
     * 使用该器材的数量
     */
    @ApiModelProperty("器材数量")
    private Integer equipNumber;
}
