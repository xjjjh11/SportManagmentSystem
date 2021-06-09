package com.sms.placemgmt.mapper;

import com.sms.placemgmt.pojo.Place;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/7 19:08
 */
public interface PlaceMapper {

    /**
     * 添加场地
     * @param place 场地信息
     * @return
     */
    @Insert("INSERT INTO tb_sms_place\n" +
            "(id,type,place_name,status)\n" +
            "VALUE\n" +
            "(default,#{type},#{placeName},#{status})")
    Integer insPlace(Place place);

    /**
     * 查询全部场地类型
     * @return
     */
    @Select("SELECT type FROM tb_sms_place")
    List<Integer> selAllPlaceTypes();

    /**
     * 查询某个类型下的所有场地
     * @param type
     * @return
     */
    @Select("SELECT id,place_name \n" +
            "FROM tb_sms_place\n" +
            "WHERE type = #{type}")
    List<Place> selAllPlaceByType(Integer type);

    /**
     * 通过场地名称和类型查询场地的状态
     * @param placeName 场地名称
     * @param placeType 场地类型
     * @return
     */
    @Select("SELECT status FROM tb_sms_place\n" +
            "WHERE place_name = #{placeName} AND type = #{placeType}")
    Integer selStatusByPlaceNameAndType(String placeName,Integer placeType);

    /**
     * 更新场地状态
     * @param placeStatus 场地状态
     * @param placeName 场地名称
     * @param placeType 场地类型
     * @return
     */
    @Update("UPDATE tb_sms_place SET status = #{placeStatus}\n" +
            "WHERE place_name = #{placeName} AND type = #{placeType}")
    Integer updStatusByPlaceNameAndType(Integer placeStatus,String placeName,Integer placeType);

    /**
     * 根据所选场地类型和时间，查询对应的收费标准
     * @param placeType
     * @param week
     * @param timeZone
     * @return
     */
    @Select("SELECT rates FROM tb_sms_place_rate WHERE place_type = #{placeType} AND time_id\n" +
            "IN\n" +
            "(SELECT id FROM tb_sms_time_slot WHERE `week` = #{week} AND time_zone = #{timeZone})")
    Integer selRatesByPlaceTypeAndTime(Integer placeType,Integer week,String timeZone);

    /**
     * 时间id为0，根据所选场地类型，查询对应的收费标准
     * @param placeType 场地类型
     * @param timeId 时间id为0
     * @return
     */
    @Select("SELECT rates FROM tb_sms_place_rate WHERE place_type = #{placeType} AND time_id = #{timeId}")
    Integer selRatesByPlaceTypeAndTimeId(Integer placeType,Integer timeId);

    /**
     * 场地空闲时，通过场地id删除场地
     * @param id
     * @return
     */
    @Delete("DELETE FROM tb_sms_place WHERE id = #{id}")
    Integer delPlaceById(Integer id);


}
