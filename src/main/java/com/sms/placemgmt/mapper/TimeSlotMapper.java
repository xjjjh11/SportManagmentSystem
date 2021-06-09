package com.sms.placemgmt.mapper;

import com.sms.placemgmt.pojo.TimeSlot;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/8 17:49
 */
public interface TimeSlotMapper {

    @Update("UPDATE tb_sms_time_slot SET enabled = 1\n" +
            "WHERE week = #{week} AND time_zone = #{timeZone}")
    Integer updTimeEnabledByWeekAndTimeZone(Integer week,String timeZone);

    /**
     * 查询全部时间段信息
     * @return
     */
    @Select("SELECT id,week,time_zone FROM tb_sms_time_slot")
    List<TimeSlot> selAllTime();
}
