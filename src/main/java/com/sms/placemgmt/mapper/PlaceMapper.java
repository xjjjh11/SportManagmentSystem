package com.sms.placemgmt.mapper;

import com.sms.placemgmt.pojo.Place;
import org.apache.ibatis.annotations.Insert;

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
            "VALUES\n" +
            "(default,#{type},#{placeName},#{state},#{timeId})")
    Integer insPlace(Place place);
}
