package com.sms.equipmgmt.service.impl;

import com.sms.equipmgmt.mapper.EquipmentMapper;
import com.sms.equipmgmt.mapper.EquipmentRentMapper;
import com.sms.equipmgmt.pojo.Equipment;
import com.sms.equipmgmt.pojo.EquipmentRent;
import com.sms.equipmgmt.service.EquipmentRentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/10 19:55
 */
@Slf4j
@Service
public class EquipmentRentServiceImpl implements EquipmentRentService {

    @Autowired
    private EquipmentRentMapper equipmentRentMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;

    /**
     * 租借器材
     *      成功租借器材后更新原本的器材表，器材数量 = 原本器材数 - 租借数，器材租用数量 = 租借数；
     *      并计算出租借金额 = 租借数 * 租借时长
     * @param equipmentRent
     * @param number 器材原本数量
     * @return
     */
    @Override
    public Integer rentEquipment(EquipmentRent equipmentRent,Integer number) {
        Integer equipType = equipmentRent.getEquipType();
        Integer rentNumber = equipmentRent.getRentNumber();
        Integer duration = equipmentRent.getDuration();
        // 该类型器材的租借费用/h
        Double rates = equipmentMapper.selEquipRatesByType(equipType);
        // 计算租用金额 = 租借时长 * 每小时租金 * 数量
        Double rentRates = duration * rates * rentNumber;
        // 被租借后的器材数量
        number -= rentNumber;
        // 更新器材数量
        Integer flag = equipmentMapper.updEquipmentNumAndRentNumber(equipType, number,rentNumber);
        if (flag == 1){
            equipmentRent.setRentRate(rentRates);
            return equipmentRentMapper.insEquipRent(equipmentRent);
        }
        log.info("更新器材数量失败！");
        return 0;
    }

    /**
     * 回收器材
     *      1、把租借数量添加回器材可用数量上
     *      2、删除器材租借信息
     * @param id
     * @param eType 器材类型
     * @param rentNum 租借数
     * @param number 租借后的器材数量
     * @return
     */
    @Override
    public Integer delEquipmentRent(Integer id, Integer eType, Integer rentNum,Integer number) {
        // 修改器材数
        number += rentNum;
        rentNum = 0;
        // 更新器材数量 = 器材数 + 租借数
        Integer flag = equipmentMapper.updEquipmentNumAndRentNumber(eType, number, rentNum);
        // 更新成功 删除租借信息
        if (flag == 1)
            return equipmentRentMapper.delEquipRent(id);
        return 0;
    }

    @Override
    public List<EquipmentRent> findRentEquipByUserNum(String userNum) {
        return equipmentRentMapper.selRentEquipByUserNum(userNum);
    }

    @Override
    public List<EquipmentRent> findAllRentEquip() {
        return equipmentRentMapper.selAllRentEquip();
    }
}
