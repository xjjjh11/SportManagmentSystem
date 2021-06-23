package com.sms.equipmgmt.mapper;

import com.sms.equipmgmt.pojo.EquipmentRent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/10 19:50
 */
public interface EquipmentRentMapper {

    /**
     * 租借器材
     * @param equipmentRent
     * @return
     */
    @Insert("INSERT INTO tb_sms_equip_rent\n" +
            "VALUES\n" +
            "(DEFAULT,#{username},#{userNumber},#{duration},#{phone},#{rentNumber},#{rentTime},#{rentRate},#{equipType})")
    Integer insEquipRent(EquipmentRent equipmentRent);

    /**
     * 归还器材
     * @param id
     * @return
     */
    @Delete("DELETE FROM tb_sms_equip_rent WHERE id = #{id}")
    Integer delEquipRent(Integer id);

    /**
     * 查询当前用户的全部租借信息
     * @param userNumber
     * @return
     */
    @Select("SELECT * FROM tb_sms_equip_rent WHERE user_number = #{userNumber}")
    List<EquipmentRent> selRentEquipByUserNum(String userNumber);

    /**
     * 查询系统中全部租借信息
     * @return
     */
    @Select("SELECT * FROM tb_sms_equip_rent")
    List<EquipmentRent> selAllRentEquip();
}
