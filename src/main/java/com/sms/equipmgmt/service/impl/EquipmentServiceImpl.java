package com.sms.equipmgmt.service.impl;

import com.sms.equipmgmt.common.EquipmentCommon;
import com.sms.equipmgmt.mapper.EquipmentMapper;
import com.sms.equipmgmt.pojo.Equipment;
import com.sms.equipmgmt.service.EquipmentService;
import com.sms.moneymgmt.common.MoneyCommon;
import com.sms.moneymgmt.mapper.MoneyMapper;
import com.sms.moneymgmt.pojo.Money;
import com.sms.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private MoneyMapper moneyMapper;

    /**
     * 新增器材
     *      器材类型已经固定，只要输入该类型器材数量和购入金额
     *      器材数量是在原有的基础上添加
     * @param equipment
     * @return
     */
    @Override
    public Integer addEquipment(Equipment equipment) {
        Money money = new Money();
        Double buyRates = equipment.getBuyRates();
        Integer number = equipment.getNumber();
        // 购置器材的花费 = 器材的单个费用 * 数量
        Double buyEquipRate = number * buyRates;
        // 购置器材的花费，存到运营信息中
        money.setMoney(buyEquipRate);
        money.setYear(DateUtil.getCurrentYear());
        money.setMonth(DateUtil.getCurrentMonth());
        money.setDay(DateUtil.getCurrentDay());
        money.setType(MoneyCommon.BUY_EQUIPMENT_MONEY);
        if (moneyMapper.insEquipMoney(money) == 1) log.info("购买器材花费新增成功");
        else {
            log.info("购买器材花费新增失败");
            return -1;
        }
        // 新增器械时，租用和维修数量默认为0
//        equipment.setRentNum(EquipmentCommon.INIT_RENT_NUM);
//        equipment.setServiceNum(EquipmentCommon.INIT_SERVICE_NUM);
        // 原有的器材数量
        Integer preNumber = equipmentMapper.selEquipNumberByType(equipment.getType());
        // 添加后的器材总数量
        number += preNumber;
        equipment.setNumber(number);
        return equipmentMapper.updEquipment(equipment);
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
        Money money = new Money();
        if (serviceNum != 0) {
            // 器材报修金额（自定义的） = 2 * 器材租用金额 * 报修数量
            Double eRate = equipmentMapper.selRateByType(type);
            Double destroyRates = 2 * serviceNum * eRate;
            // 器材维修的金额，存到运营信息中
            money.setMoney(destroyRates);
            money.setYear(DateUtil.getCurrentYear());
            money.setMonth(DateUtil.getCurrentMonth());
            money.setDay(DateUtil.getCurrentDay());
            money.setType(MoneyCommon.EQUIPMENT_DESTROY_MONEY);
            if (moneyMapper.insEquipMoney(money) == 1) log.info("器材报修金额新增成功");
            else {
                log.info("器材报修金额新增失败");
                return null;
            }
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
