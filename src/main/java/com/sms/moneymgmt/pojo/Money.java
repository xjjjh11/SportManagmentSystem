package com.sms.moneymgmt.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("运营金额信息")
public class Money {
    /**
     * 运营金额id
     */
    @ApiModelProperty("运营金额id")
    private Integer id;

    /**
     * 运营金额
     */
    @ApiModelProperty("运营金额")
    private Double money;

    /**
     * 年
     */
    @ApiModelProperty("年")
    private Integer year;

    /**
     * 月
     */
    @ApiModelProperty("月")
    private Integer month;

    /**
     * 日
     */
    @ApiModelProperty("日")
    private Integer day;

    /**
     * 金额类型
     * 0场地被使用的收费
     * 1购置器材的花费
     * 2出租器材的收费
     * 3用户损毁器材的赔偿金
     */
    @ApiModelProperty("金额类型")
    private Integer type;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 某年月金额总和
     */
    @ApiModelProperty("某年月金额总和")
    private Integer sumYM;

    /**
     * 场地被使用的收费日总和
     */
    @ApiModelProperty("场地被使用的收费日总和")
    private Integer daySumType0;

    /**
     * 购置器材的花费日总和
     */
    @ApiModelProperty("购置器材的花费日总和")
    private Integer daySumType1;

    /**
     * 出租器材的收费日总和
     */
    @ApiModelProperty("出租器材的收费日总和")
    private Integer daySumType2;

    /**
     * 用户损毁器材的赔偿金日总和
     */
    @ApiModelProperty("用户损毁器材的赔偿金日总和")
    private Integer daySumType3;

    /**
     * 某类型月总和
     */
    @ApiModelProperty("某类型月总和")
    private Integer value;

    /**
     * 月总和的类型
     */
    @ApiModelProperty("月总和的类型")
    private String name;
}

