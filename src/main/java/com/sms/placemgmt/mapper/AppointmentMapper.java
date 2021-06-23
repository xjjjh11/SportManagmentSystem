package com.sms.placemgmt.mapper;

import com.sms.placemgmt.pojo.Appointment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/8 8:04
 */
public interface AppointmentMapper {

    /**
     * 预约场地
     * @param appointment
     * @return
     */
    @Insert("INSERT INTO tb_sms_appointment\n" +
            "VALUES\n" +
            "(default,#{userNumber},#{username},#{phone},#{appointType},#{placeType},#{placeName},#{placeStatus},#{week},#{timeZone})")
    Integer insAppoint(Appointment appointment);

    /**
     * 根据场地类型和预约时间查询场地的预约信息
     * @param placeType
     * @param week
     * @param timeZone
     * @return
     */
    @Select("SELECT `week`,time_zone,place_name,place_status FROM tb_sms_appointment\n" +
            "WHERE place_type = #{placeType} AND `week` = #{week} AND time_zone = #{timeZone}")
    List<Appointment> selAppointByPlaceTypeAndTime(Integer placeType,Integer week,String timeZone);

    /**
     * 根据场地类型和名称查询用户的预约时间
     * @param placeName
     * @param placeType
     * @return
     */
    @Select("SELECT place_status,week,time_zone FROM tb_sms_appointment\n" +
            "WHERE place_name = #{placeName} AND place_type = #{placeType}")
    List<Appointment> selAppointTimeByPlaceNameAndType(String placeName,Integer placeType);

    @Select("SELECT place_status,time_zone FROM tb_sms_appointment\n" +
            "WHERE place_name = #{placeName} AND place_type = #{placeType} AND week = #{week}")
    List<Appointment> selAppointWeekByPlaceNameAndType(String placeName,Integer placeType,Integer week);
    /**
     * 查询我预约的场地信息
     * @return
     */
    @Select("SELECT * FROM tb_sms_appointment\n" +
            "WHERE user_number = #{userNumber}")
    List<Appointment> selAppointsByUserNumber(String userNumber);

    /**
     * 查询全部预约信息
     * @return
     */
    @Select("SELECT * FROM tb_sms_appointment")
    List<Appointment> selAllAppoints();

    /**
     * 取消预约
     * @param appointId
     * @return
     */
    @Delete("DELETE FROM tb_sms_appointment\n" +
            "WHERE\n" +
            "id = #{appointId}")
    Integer delAppointByPlaceAndTime(Integer appointId);


    @Update("UPDATE tb_sms_appointment SET username = #{username},phone = #{phone}\n" +
            "WHERE id = #{id}")
    Integer updAppointByAccountId(String username, String phone, @Param("id") Integer accountId);

}
