package com.sms.equipmgmt.common;

/**
 * 器材类型和其收费标准（元/小时）
 * @author Jared
 * @date 2021/6/10 15:47
 */
public class EquipmentCommon {
    /**
     * 羽毛球拍
     */
    public final static Integer BADMINTON_RACKET= 0;
    /**
     * 毽球
     */
    public final static Integer SHUTTLECOCK = 1;
    /**
     * 排球
     */
    public final static Integer VOLLEYBALL = 2;
    /**
     * 篮球
     */
    public final static Integer BASKETBALL = 3;
    /**
     * 足球
     */
    public final static Integer FOOTBALL = 4;
    /**
     * 乒乓球
     */
    public final static Integer TABLE_TENNIS_BAT = 5;
    /**
     * 保龄球
     */
    public final static Integer BOWLING = 6;
    /**
     * 初始化被租借的器材数量
     */
    public final static Integer INIT_RENT_NUM = 0;
    /**
     * 初始化被维修的器材数量
     */
    public final static Integer INIT_SERVICE_NUM = 0;

    /**--------------------       器材收费标准      ----------------------------------------*/
    /**
     * 羽毛球拍收费
     */
    public final static Double BADMINTON_RACKET_RATES= 3.00;
    /**
     * 毽球
     */
    public final static Double SHUTTLECOCK_RATES = 1.00;
    /**
     * 排球
     */
    public final static Double VOLLEYBALL_RATES = 2.00;
    /**
     * 篮球
     */
    public final static Double BASKETBALL_RATES = 5.00;
    /**
     * 足球
     */
    public final static Double FOOTBALL_RATES = 4.00;
    /**
     * 乒乓球拍
     */
    public final static Double TABLE_TENNIS_BAT_RATES = 2.00;
    /**
     * 保龄球
     */
    public final static Double BOWLING_RATES = 6.00;
}
