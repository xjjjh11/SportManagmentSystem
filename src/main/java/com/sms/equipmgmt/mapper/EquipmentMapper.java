package com.sms.equipmgmt.mapper;

import com.sms.equipmgmt.pojo.Equipment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/10 15:43
 */
public interface EquipmentMapper {

    /**
     * 新增器材
     * @param equipment
     * @return
     */
    @Insert("INSERT INTO tb_sms_equipment\n" +
            "VALUES\n" +
            "(#{type},#{number},#{rentNum},#{serviceNum},#{rates})")
    Integer insEquipment(Equipment equipment);

    /**
     * 新增器材
     *      器材类型已经固定，只要输入该类型器材数量和购入金额
     * @param equipment
     * @return
     */
    @Insert("UPDATE tb_sms_equipment\n" +
            "SET number=#{number},buy_rates=#{buyRates}\n" +
            "WHERE type = #{type}")
    Integer updEquipment(Equipment equipment);

    /**
     * 报修器材
     * @param type 器材类型
     * @param serviceNum 报修器材的数量
     * @param number 原有数量 - 报修的数量
     * @return
     */
    @Update("UPDATE tb_sms_equipment SET number = #{number},service_num = #{serviceNum}\n" +
            "WHERE type = #{type}")
    Integer updEquipmentByType(Integer type,Integer serviceNum,Integer number);

    /**
     * 查询器材可用数量和报修数量
     * @param type
     * @return
     */
    @Select("SELECT number,service_num FROM tb_sms_equipment\n" +
            "WHERE type = #{type}")
    Equipment selEquipNumAndServiceNum(Integer type);

    /**
     * 修改器材数量
     * @param type 器材类型
     * @param number 修改后的器材数量
     * @return
     */
    @Update("UPDATE tb_sms_equipment SET number = #{number}\n" +
            "WHERE type = #{type}")
    Integer updEquipmentNumByType(Integer type,Integer number);

    /**
     * 查询器材全部器材信息
     */
    @Select("SELECT * FROM tb_sms_equipment")
    List<Equipment> selAllEquipment();

    /**
     * 查询当前器材数量
     */
    @Select("SELECT number FROM tb_sms_equipment\n" +
            "WHERE type = #{type}")
    Integer selEquipNumberByType(Integer type);

    /**
     * 查询当前用户租借器材数
     */
    @Select(" SELECT rent_number FROM tb_sms_equip_rent \n" +
            " WHERE id = #{id} AND equip_type = #{equipType} AND user_number = #{userNumber}")
    Integer selEquipRentNumByUserNumAndType(Integer id,Integer equipType,String userNumber);

    /**
     * 查询全部租借器材数
     */
    @Select(" SELECT rent_number FROM tb_sms_equip_rent \n" +
            " WHERE equip_type = #{equipType} ")
    List<Integer> selEquipRentNumByType(Integer equipType);

    /**
     * 更新器材数量
     *      租借或者归还器材
     * @param type
     * @return
     */
    @Update("UPDATE tb_sms_equipment SET number = #{number},rent_num = #{rentNumber}\n" +
            "WHERE type = #{type}")
    Integer updEquipmentNumAndRentNumber(Integer type,Integer number,Integer rentNumber);

    /**
    * 查询器材租借费用
    * @param type
    * @return
    */
    @Select("SELECT rates FROM tb_sms_equipment\n" +
            "WHERE type = #{type}")
    Double selEquipRatesByType(Integer type);

    /**
     * 查询全部正在维修的器材类型和数量
     * @return
     */
    @Select("SELECT type,number,service_num FROM tb_sms_equipment WHERE service_num > 0")
    List<Equipment> selRepairNumbers();

    /**
     * 查询器材收费标准
     * @return
     */
    @Select("SELECT rates FROM tb_sms_equipment WHERE type = #{type}")
    Double selRateByType(Integer type);


}
