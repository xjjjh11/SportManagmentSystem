package com.sms.placemgmt.service;

import com.sms.placemgmt.pojo.Appointment;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/8 7:59
 */
public interface AppointmentService {

    /**
     * 场地预约
     * @param appointment
     * @return
     */
    Integer orderPlace(Appointment appointment);

    /**
     * 查询全部预约信息
     * @return
     */
    List<Appointment> findAllAppoints();

    /**
     * 取消预约后，并把该时间段的场地状态改为可使用
     * @param appointId
     * @return
     */
    Integer cancelAppoint(Integer appointId,Integer placeType,String placeName);

    /**
     * 修改预约信息
     * @param username
     * @param phone
     * @param accountId
     * @return
     */
    Integer modifyAppoint(String username, String phone,Integer accountId);


}
