package com.sms.equipmgmt.service;

import com.sms.equipmgmt.pojo.EquipmentRent;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/10 19:54
 */
public interface EquipmentRentService {

    /**
     * 租借器材
     * @param equipmentRent
     * @return
     */
    Integer rentEquipment(EquipmentRent equipmentRent,Integer number);

    /**
     * 器材回收
     * @param id
     * @param eType
     * @param rentNum
     * @return
     */
    Integer delEquipmentRent(Integer id,Integer eType,Integer rentNum,Integer number);

    /**
     * 查询当前用户的全部租借信息
     * @param userNum
     * @return
     */
    List<EquipmentRent> findRentEquipByUserNum(String userNum);

    /**
     * 查询全部租借信息
     * @return
     */
    List<EquipmentRent> findAllRentEquip();
}
