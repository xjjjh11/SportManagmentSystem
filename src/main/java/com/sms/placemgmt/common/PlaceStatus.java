package com.sms.placemgmt.common;

/**
 * @author Jared
 * @date 2021/6/7 21:16
 */
public class PlaceStatus {
    /**
     * 空闲
     */
    public final static Integer FREE = 0;
    /**
     * 使用中
     */
    public final static Integer USING = 1;
    /**
     * 被预约
     */
    public final static Integer ORDERED = 2;
    /**
     * 上课
     */
    public final static Integer CLASSING = 3;
    /**
     * 校队训练
     */
    public final static Integer TRAIN = 4;
    /**
     * 比赛
     */
    public final static Integer CONTEST = 5;

    /**
     * 预约成功
     */
    public final static Integer APPOINT_SUCCESS = 6;
    /**
     * 预约失败
     */
    public final static Integer APPOINT_FAILURE = -1;
    /**
     * 时间段已被禁用
     */
    public final static Integer TIME_FORBIDDEN = -2;




}
