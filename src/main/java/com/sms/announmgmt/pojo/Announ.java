package com.sms.announmgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("公告信息")
public class Announ {
    /**
     * 公告id
     */
    @ApiModelProperty("公告id")
    private Integer id;

    /**
     * 公告内容
     */
    @ApiModelProperty("公告内容")
    private String content;

    /**
     * 公告发送时间
     */
    @ApiModelProperty("公告发送时间")
    private String time;

    /**
     * 公告类型
     * 0为场馆公告，1为馆内设施罚款公告，2为裁判简介，3为其他公告
     */
    @ApiModelProperty("公告类型")
    private Integer type;
}
