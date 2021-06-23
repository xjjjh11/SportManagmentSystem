package com.sms.placemgmt.service.impl;

import com.sms.moneymgmt.common.MoneyCommon;
import com.sms.moneymgmt.mapper.MoneyMapper;
import com.sms.moneymgmt.pojo.Money;
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

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private MoneyMapper moneyMapper;
    /**
     * 场地免费使用时间
     */
    private final String freeTimeZone = "16:00~18:00";

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
        Integer week = appointment.getWeek();
        String timeZone = appointment.getTimeZone();
        Integer appointType = appointment.getAppointType();

        if (placeName != null && placeType != null){
            // 默认设置为个人预约
            if (appointType < 0) {
                appointType = 0;
                appointment.setAppointType(appointType);
            }
            // 通过场地名称和类型查询当前场地的状态
            Integer placeStatus = placeMapper.selStatusByPlaceNameAndTypeAndTime(placeName, placeType,week ,timeZone);
            log.info("placeStatus:"+placeStatus);
            // 当前场地状态为null，则默认为可预约
            if (placeStatus == null) placeStatus = 0;
            int flag=0,flag1=0,flag2=0;
            // 场地当前状态
            switch (placeStatus){
                // 可预约
                case 0:
                    flag1 = appointmentMapper.insAppoint(appointment);
                    log.info("场地名："+placeName+" 场地类型："+placeType);
                    // 根据 appointType 预约类型，修改场地状态
                    switch (appointType){
                        // 普通用户预约场地
                        case 0:
                            // 修改场地状态为被预约
                            flag2 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.ORDERED, placeName, placeType);
                            flag = placeMapper.updStatusByPlaceNameAndType2(PlaceStatus.ORDERED, placeName, placeType,week,timeZone);
                            log.info("普通用户预约：flag1:"+flag1+"   flag2:"+flag2+" flag："+flag);
                            // 预约成功，新增场地预约花费到运营金额
                            if (flag1 == 1 && flag2 == 1 && flag == 1){
                                // 星期一到星期五的16:00~18:00免费使用全部场地，且每次只能预约两小时
                                if (week <= 5 && week >= 1 && timeZone.equals(freeTimeZone))
                                    log.info("该时间段场地免费使用，无需收费！");
                                else {
                                    Money money = new Money();
                                    // 在收费的情况下，数据库中的timeId全部默认为0，且每次只能预约两小时
                                    Integer timeId = 0;
                                    Double rates = placeMapper.selRatesByPlaceTypeAndTimeId(placeType, timeId);
                                    Double rentPlaceRate = rates * 2;
                                    Integer year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
                                    Integer month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
                                    Integer day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
                                    money.setMoney(rentPlaceRate);
                                    money.setYear(year);
                                    money.setMonth(month);
                                    money.setDay(day);
                                    money.setType(MoneyCommon.PLACE_MONEY);
                                    if (moneyMapper.insEquipMoney(money) == 1) log.info("场地费用新增成功");
                                    else {
                                        log.info("场地费用新增失败");
                                        return -1;
                                    }
                                }
                                return PlaceStatus.APPOINT_SUCCESS;
                            }
                            break;
                        // 校队训练
                        case 1:
                            flag2 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.TRAIN, placeName, placeType);
                            flag = placeMapper.updStatusByPlaceNameAndType2(PlaceStatus.TRAIN, placeName, placeType,week,timeZone);
                            log.info("校队训练预约 flag1:"+flag1+"   flag3:"+flag2+"   flag:"+flag);
                            if (flag1 == 1 && flag2 == 1 && flag == 1)
                                return PlaceStatus.APPOINT_SUCCESS;
                            break;
                        // 上课预约
                        case 2:
                            flag2 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.CLASSING, placeName, placeType);
                            flag = placeMapper.updStatusByPlaceNameAndType2(PlaceStatus.CLASSING, placeName, placeType,week,timeZone);
                            log.info("上课预约 flag1:"+flag1+"   flag4:"+flag2+"   flag:"+flag);
                            if (flag1 == 1 && flag2 == 1 && flag == 1)
                                return PlaceStatus.APPOINT_SUCCESS;
                            break;
                        // 比赛预约
                        case 3:
                            flag2 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.CONTEST, placeName, placeType);
                            flag = placeMapper.updStatusByPlaceNameAndType2(PlaceStatus.CONTEST, placeName, placeType,week,timeZone);
                            log.info("比赛预约 flag1:"+flag1+"   flag5:"+flag2+"   flag:"+flag);
                            if (flag1 == 1 && flag2 == 1 && flag == 1){
                                // 星期一到星期五的16:00~18:00免费使用全部场地，且每次只能预约两小时
                                if (week <= 5 && week >= 1 && timeZone.equals(freeTimeZone))
                                    log.info("该时间段场地免费使用，无需收费！");
                                else {
                                    Money money = new Money();
                                    // 在收费的情况下，数据库中的timeId全部默认为0，且每次只能预约两小时
                                    Integer timeId = 0;
                                    Double rates = placeMapper.selRatesByPlaceTypeAndTimeId(placeType, timeId);
                                    Double rentPlaceRate = rates * 2;
                                    Integer year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
                                    Integer month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
                                    Integer day = Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
                                    money.setMoney(rentPlaceRate);
                                    money.setYear(year);
                                    money.setMonth(month);
                                    money.setDay(day);
                                    money.setType(MoneyCommon.PLACE_MONEY);
                                    if (moneyMapper.insEquipMoney(money) == 1) log.info("场地费用新增成功");
                                    else {
                                        log.info("场地费用新增失败");
                                        return -1;
                                    }
                                }
                                return PlaceStatus.APPOINT_SUCCESS;
                            }
                            break;
                        }
                    break;
                case 1:
                    return PlaceStatus.ORDERED;
                case 2:
                    return PlaceStatus.CLASSING;
                case 3:
                    return PlaceStatus.TRAIN;
                case 4:
                    return PlaceStatus.CONTEST;
            }
        }
        log.info("场地名或场地类型为空！ ");
        return PlaceStatus.APPOINT_FAILURE;
    }

    /**
     * 查询全部预约信息
     * @return
     */
    @Override
    public List<Appointment> findMyAppoints(String userNumber) {
        return appointmentMapper.selAppointsByUserNumber(userNumber);
    }

    /**
     * 取消预约后，并把该时间段的场地状态改为可使用
     * @param appointId
     * @return
     */
    public Integer cancelAppoint(Integer appointId,Integer placeType,String placeName){

        Integer flag1 = placeMapper.updStatusByPlaceNameAndType(PlaceStatus.FREE, placeName, placeType);
        Integer flag2 = placeMapper.updStatusByAppointId(appointId,PlaceStatus.FREE);
        if (flag1 == 1 && flag2 == 1){
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
