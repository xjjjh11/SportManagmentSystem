package com.sms.equipmgmt.service.impl;

import com.sms.equipmgmt.common.EquipmentCommon;
import com.sms.equipmgmt.mapper.EquipmentMapper;
import com.sms.equipmgmt.pojo.Equipment;
import com.sms.equipmgmt.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author Jared
 * @date 2021/6/10 16:04
 */
@Slf4j
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    /**
     * 新增器材
     * @param equipment
     * @return
     */
    @Override
    public Integer addEquipment(Equipment equipment) {
        Double rates = equipment.getRates();
        Integer number = equipment.getNumber();
        // 购置器材的花费 = 器材的单个费用 * 数量
        Double buyEquipRate = number * rates;
        // 购置器材的花费，需要存到运营信息中

        equipment.setRentNum(EquipmentCommon.INIT_RENT_NUM);
        equipment.setServiceNum(EquipmentCommon.INIT_SERVICE_NUM);
        return equipmentMapper.insEquipment(equipment);
    }

    /**
     * 报修器材
     * @param type 器材类型
     * @param serviceNum 报修器材的数量
     * @param number 原有数量 - 报修的数量
     * @return
     */
    @Override
    public Equipment repairEquipmentByType(Integer type, Integer serviceNum, Integer number) {
        if (serviceNum != 0) {
            number -= serviceNum;
            if (equipmentMapper.updEquipmentByType(type, serviceNum, number) == 1){
                return equipmentMapper.selEquipNumAndServiceNum(type);
            }
        }
        return equipmentMapper.selEquipNumAndServiceNum(type);
    }

    /**
     * 修改器材数量，并返回修改后的值
     * @param type
     * @param number 更新后的器材数量
     * @return
     */
    @Override
    public Integer modifyEquipmentNumByType(Integer type, Integer number) {
        return equipmentMapper.updEquipmentNumByType(type,number);
    }

    @Override
    public List<Equipment> findAllEquipments() {
        return equipmentMapper.selAllEquipment();
    }

    /**
     * 查询所有正在维修的器材类型和数量
     * @return
     */
    @Override
    public List<Equipment> findRepairEquipNum() {
        return equipmentMapper.selRepairNumbers();
    }

    /**
     * 查询某类型器材的收费标准
     * @param type 器材类型
     * @return
     */
    @Override
    public Double findRatesByType(Integer type) {
        return equipmentMapper.selRateByType(type);
    }
}
