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
     * 被预约
     */
    public final static Integer ORDERED = 1;
    /**
     * 上课
     */
    public final static Integer CLASSING = 2;
    /**
     * 校队训练
     */
    public final static Integer TRAIN = 3;
    /**
     * 比赛
     */
    public final static Integer CONTEST = 4;

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
