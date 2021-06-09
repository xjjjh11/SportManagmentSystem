package com.sms.placemgmt.service.impl;

import com.sms.placemgmt.common.PlaceStatus;
import com.sms.placemgmt.mapper.AppointmentMapper;
import com.sms.placemgmt.mapper.PlaceMapper;
import com.sms.placemgmt.mapper.TimeSlotMapper;
import com.sms.placemgmt.pojo.Appointment;
import com.sms.placemgmt.service.AppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约操作
 * @author Jared
 * @date 2021/6/8 8:00
 */
@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private PlaceMapper placeMapper;

    /**
     * 场地预约
     *      1、先根据输入的场地类型和名称判断该场地状态是否为可预约
     *          若为可预约，场地状态返回0，插入数据，并根据 预约类型 更改 场地状态
     *          若不可预约，场地状态返回 > 0，显示被预约的类型
     * @param appointment
     * @return
     */
    @Override
    public Integer orderPlace(Appointment appointment) {
        String placeName = appointment.getPlaceName();
        Integer placeType = appointment.getPlaceType();
        Integer appointType = appointment.getAppointType();

        if (placeName != null && placeType != null){
            // 默认设置为个人预约
            if (appointType < 0) {
                appointType = 0;
                appointment.setAppointType(appointType);
            }
            // 通过场地名称和类型查询当前场地的状态
            Integer status = placeMapper.selStatusByPlaceNameAndType(placeName, placeType);
            if (status !=null){
                // 场地当前状态
                switch (status){
                    // 可预约
                    case 0:
                        Integer flag1 = appointmentMapper.insAppoint(appointment);
                        // 根据 appointType 预约类型，修改场地状态
                        switch (appointType){
                            // 普通用户预约场地
                            case 0:
                                // 修改场地状态为被预约
                                Integer flag2 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.ORDERED, placeName, placeType);
                                log.info("insAppoint:"+flag1+"   uodStatus:"+flag2);
                                if (flag1 == 1 && flag2 == 1)
                                    return PlaceStatus.APPOINT_SUCCESS;
                                break;
                            // 校队训练
                            case 1:
                                Integer flag3 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.TRAIN, placeName, placeType);
                                log.info("insAppoint:"+flag1+"   uodStatus:"+flag3);
                                if (flag1 == 1 && flag3 == 1)
                                    return PlaceStatus.APPOINT_SUCCESS;
                                break;
                            // 上课预约
                            case 2:
                                Integer flag4 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.CLASSING, placeName, placeType);
                                log.info("insAppoint:"+flag1+"   uodStatus:"+flag4);
                                if (flag1 == 1 && flag4 == 1)
                                    return PlaceStatus.APPOINT_SUCCESS;
                                break;
                            // 比赛预约
                            case 3:
                                Integer flag5 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.CONTEST, placeName, placeType);
                                log.info("insAppoint:"+flag1+"   uodStatus:"+flag5);
                                if (flag1 == 1 && flag5 == 1)
                                    return PlaceStatus.APPOINT_SUCCESS;
                                break;
                            }
                    case 1:
                        return PlaceStatus.USING;
                    case 2:
                        return PlaceStatus.ORDERED;
                    case 3:
                        return PlaceStatus.CLASSING;
                    case 4:
                        return PlaceStatus.TRAIN;
                    case 5:
                        return PlaceStatus.CONTEST;
                }
            }
            log.info("place status为空！ ");
            return PlaceStatus.APPOINT_FAILURE;
        }
        log.info("场地名或场地类型为空！ ");
        return PlaceStatus.APPOINT_FAILURE;
    }

    /**
     * 查询全部预约信息
     * @return
     */
    @Override
    public List<Appointment> findAllAppoints() {
        return appointmentMapper.selAllAppoints();
    }

    /**
     * 取消预约后，并把该时间段的场地状态改为可使用
     * @param appointId
     * @return
     */
    public Integer cancelAppoint(Integer appointId,Integer placeType,String placeName){

        Integer flag = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.FREE, placeName, placeType);
        if (flag == 1){
            log.info("场地状态变为可使用！");
            return appointmentMapper.delAppointByPlaceAndTime(appointId);
        }else {
            log.info("场地状态修改失败！");
            return 0;
        }
    }

    /**
     * 修改预约信息
     * @param username
     * @param phone
     * @param accountId
     * @return
     */
    public Integer modifyAppoint(String username, String phone,Integer accountId){
        if (username != null && phone != null)
            return appointmentMapper.updAppointByAccountId(username,phone,accountId);
        else {
            log.info("用户名或密码为空，请重新输入！");
            return -1;
        }
    }
}
