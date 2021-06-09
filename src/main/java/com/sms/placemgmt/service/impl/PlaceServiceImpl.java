package com.sms.placemgmt.service.impl;

import com.sms.placemgmt.mapper.AppointmentMapper;
import com.sms.placemgmt.mapper.PlaceMapper;
import com.sms.placemgmt.mapper.TimeSlotMapper;
import com.sms.placemgmt.pojo.Appointment;
import com.sms.placemgmt.pojo.Place;
import com.sms.placemgmt.pojo.TimeSlot;
import com.sms.placemgmt.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Jared
 * @date 2021/6/7 19:17
 */
@Slf4j
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceMapper placeMapper;
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
    private final String freeTimeZone = "16:00~18:00";

    /**
     * 添加场地
     * @param place
     * @return
     */
    @Override
    public Integer addPlace(Place place) {
        return placeMapper.insPlace(place);
    }

    /**
     * 查询全部场地类型
     * @return
     */
    @Override
    public List<Integer> findPlaceTypes() {
        return placeMapper.selAllPlaceTypes();
    }

    /**
     * 根据场地类型查询全部场地信息
     * @param placeType
     * @return
     */
    @Override
    public List<Place> findAllPlaceByType(Integer placeType) {
        return placeMapper.selAllPlaceByType(placeType);
    }

    /**
     * 查询某个时间段下，某种类型的，空闲场地
     * 即除去预约表中已有的信息，返回其余全部场地信息
     *      1、根据预约信息匹配 时间段 和 场地名
     *          相同，该场地在集合中删除
     *          不同，继续步骤1，直至遍历完全部预约信息，
     * @param placeType
     * @param week
     * @param timeZone
     * @return
     */
    @Override
    public List<Place> findPlaceByTypeAndTime(Integer placeType, Integer week,String timeZone) {
        // 查询出全部该类型的场地
        List<Place> places = placeMapper.selAllPlaceByType(placeType);
        // 查询出该类型和该时间段的预约信息
        List<Appointment> appointments = appointmentMapper.selAppointByPlaceTypeAndTime(placeType, week, timeZone);

        if(places != null) {
            Iterator<Place> placeIterator = places.iterator();
            while (placeIterator.hasNext()) {
                Place place = placeIterator.next();
                for (Appointment appointment : appointments) {
                    // 如果时间段和场地名称匹配一致，删除该场地；否则继续下一次匹配
                    if (week.equals(appointment.getWeek()) && timeZone.equals(appointment.getTimeZone())
                     && place.getPlaceName().equals(appointment.getPlaceName())) {
                        log.info("时间段匹配一致或场地名字相同，删除该场地！");
                        placeIterator.remove();
                    }
                }
            }
        }else return null;
        return places;
    }

    /**
     * 查询场地一周内的空闲时间
     *      根据场地类型和名称查询预约信息
     *          1、排除预约信息中的时间
     *          2、返回排除预约时间后的全部时间段
     * @param placeName
     * @param placeType
     * @return
     */
    @Override
    public List<TimeSlot> findEnableTimeByPlaceNameAndType(String placeName, Integer placeType) {
        List<TimeSlot> timeSlots = timeSlotMapper.selAllTime();
        List<Appointment> appointTimes = appointmentMapper.selAppointTimeByPlaceNameAndType(placeName, placeType);
        Iterator<TimeSlot> slotIter = timeSlots.iterator();
        // 判断预约信息中的时间是否与时间表中的时间相同，相同就删除时间表中的该时间
        // 直至遍历完时间表，最后返回时间表中的数据
        while (slotIter.hasNext()){
            TimeSlot timeSlot = slotIter.next();
            if (appointTimes != null){
                for (Appointment appointTime : appointTimes){
                    if (timeSlot.getWeek().equals(appointTime.getWeek())&&
                            timeSlot.getTimeZone().equals (appointTime.getTimeZone())){
                        log.info("星期"+timeSlot.getWeek()+","+timeSlot.getTimeZone()+" 时间段已被预约，从时间集合中删除！");
                        slotIter.remove();
                    }
                }
            // 预约信息中该场地没有被预约，直接返回全部时间段
            }else return timeSlots;
        }
        return timeSlots;
    }

    /**
     * 查询一周内场地全部时间
     * @param placeName
     * @param placeType
     * @return
     */
    @Override
    public Map<String,Object> findAllTime(String placeName, Integer placeType) {
        Map<String, Object> map = new HashMap<>();
        List<TimeSlot> timeSlots = timeSlotMapper.selAllTime();
        List<Appointment> appointments = appointmentMapper.selAppointTimeByPlaceNameAndType(placeName, placeType);
        Iterator<TimeSlot> timeSlotIter = timeSlots.iterator();
        // 排除全部空闲时间中，已经被预约的时间
        while (timeSlotIter.hasNext()){
            TimeSlot timeSlot = timeSlotIter.next();
            for (Appointment appointment : appointments){
                if (timeSlot.getTimeZone().equals(appointment.getTimeZone()) &&
                timeSlot.getWeek().equals(appointment.getWeek())){
                    timeSlotIter.remove();
                }
            }
        }
        // 空闲时间
        map.put("enabledTime",timeSlots);
        // 场地被预约的时间和状态
        map.put("occupyTime",appointments);
        return map;
    }

    /**
     * 根据所选场地类型和时间，查询收费标准
     *      周一至周五的16:00-18:00，全部场地免费使用
     *      其余时间都需要收费使用
     * @param placeType 场地类型
     * @param week 星期几
     * @param timeZone 时间段
     * @return
     */
    @Override
    public Integer findRateByPlaceTypeAndTime(Integer placeType, Integer week, String timeZone) {
        // 星期一到星期五的16:00~18:00免费使用全部场地
        if (week <= 5 && week >= 1 && timeZone.equals(freeTimeZone))
            return placeMapper.selRatesByPlaceTypeAndTime(placeType, week, timeZone);
        else {
            // 在收费的情况下，数据库中的timeId全部默认为0
            Integer timeId = 0;
            return placeMapper.selRatesByPlaceTypeAndTimeId(placeType,timeId);
        }
    }

    /**
     * 删除场地
     *      1、先查询预约信息中有无预约
     *      2、无，直接删除该场地
     *          有，无法删除
     * @param id
     * @param placeName
     * @param placeType
     * @return
     */
    @Override
    public Integer delPlaceById(Integer id,String placeName,Integer placeType) {
        List<Appointment> appointments = appointmentMapper.selAllAppoints();
        for (Appointment appointment : appointments) {
            if (placeName.equals(appointment.getPlaceName()) && placeType.equals(appointment.getPlaceType())) {
                log.info("该场地已被预约，无法删除！");
                return -1;
            }
        }
        return placeMapper.delPlaceById(id);
    }
}
