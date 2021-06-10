package com.sms.equipmgmt.service;

import com.sms.equipmgmt.pojo.Equipment;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/10 16:03
 */
public interface EquipmentService {

    /**
     * 新增器材
     * @param equipment
     * @return
     */
    Integer addEquipment(Equipment equipment);

    /**
     * 报修器材
     * @param type 器材类型
     * @param serviceNum 报修器材的数量
     * @param number 原有数量 - 报修的数量
     * @return
     */
    Equipment repairEquipmentByType(Integer type,Integer serviceNum,Integer number);

    /**
     * 修改器材数量，并返回修改后的值
     * @param type
     * @param number
     * @return
     */
    Integer modifyEquipmentNumByType(Integer type,Integer number);

    /**
     * 查询全部器材信息
     * @return
     */
    List<Equipment> findAllEquipments();

    /**
     * 查询正在维修的器材数
     * @return
     */
    List<Equipment> findRepairEquipNum();

    /**
     * 查询某类型的器材收费标准
     * @param type
     * @return
     */
    Double findRatesByType(Integer type);
}
