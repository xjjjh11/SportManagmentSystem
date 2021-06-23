package com.sms.contestmgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
@ApiModel("赛事信息")
public class Contest {
    /**
     * 赛事id
     */
    @ApiModelProperty("赛事id")
    private Integer id;

    /**
     * 赛事名称
     */
    @ApiModelProperty("赛事名称")
    private String name;

    /**
     * 赛事类型
     * 0篮球
     * 1羽毛球
     * 2乒乓球
     * 3台球
     * 4毽子
     */
    @ApiModelProperty("赛事类型")
    private Integer type;

    /**
     * 赛事场地id
     */
    @ApiModelProperty("赛事场地id")
    private Integer placeId;

    /**
     * 赛事时间段id
     */
    @ApiModelProperty("赛事时间段id")
    private Integer timeId;

    /**
     * 场地类型
     */
    @ApiModelProperty("场地类型")
    private String placeType;

    /**
     * 场地名称
     */
    @ApiModelProperty("场地名称")
    private String placeName;

    /**
     * 第几周
     */
    @ApiModelProperty("第几周")
    private Integer week;

    /**
     * 时间段
     */
    @ApiModelProperty("时间段")
    private String timeZone;
}
