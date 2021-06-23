package com.sms.equipmgmt.service.impl;

import com.sms.equipmgmt.mapper.EquipmentMapper;
import com.sms.equipmgmt.mapper.EquipmentRentMapper;
import com.sms.equipmgmt.pojo.EquipmentRent;
import com.sms.equipmgmt.service.EquipmentRentService;
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
 * @date 2021/6/10 19:55
 */
@Slf4j
@Service
public class EquipmentRentServiceImpl implements EquipmentRentService {

    @Autowired
    private EquipmentRentMapper equipmentRentMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private MoneyMapper moneyMapper;

    /**
     * 租借器材
     *      成功租借器材后更新原本的器材表，器材数量 = 原本器材数 - 租借数，器材租用数量 = 租借数；
     *      并计算出租借金额 = 租借数 * 租借时长
     * @param equipmentRent
     * @return
     */
    @Override
    public Integer rentEquipment(EquipmentRent equipmentRent) {
        Integer equipType = equipmentRent.getEquipType();
        Integer rentNumber = equipmentRent.getRentNumber();
        Integer duration = equipmentRent.getDuration();
        Integer number = equipmentMapper.selEquipNumberByType(equipType);
        // 全部器材租借数
        Integer allRentNum = 0;
        List<Integer> rentNumberList = equipmentMapper.selEquipRentNumByType(equipType);
        for (Integer rentNum : rentNumberList){
            allRentNum += rentNum;
        }
        allRentNum += rentNumber;
        // 器材租借数 <= 器材剩余数量
        if (rentNumber <= number){
            // 该类型器材的租借费用/h
            Double rates = equipmentMapper.selEquipRatesByType(equipType);
            // 计算租用金额 = 租借时长 * 每小时租金 * 数量
            Double rentRates = duration * rates * rentNumber;
            // 存入运营金额
            Money money = new Money();
            money.setMoney(rentRates);
            money.setYear(DateUtil.getCurrentYear());
            money.setMonth(DateUtil.getCurrentMonth());
            money.setDay(DateUtil.getCurrentDay());
            money.setType(MoneyCommon.EQUIPMENT_RENT_MONEY);
            if (moneyMapper.insEquipMoney(money) == 1) log.info("器材租赁费用新增成功");
            else {
                log.info("器材租赁费用新增失败");
                return -1;
            }
            // 被租借后的器材数量
            number -= rentNumber;
            // 更新器材数量
            Integer flag = equipmentMapper.updEquipmentNumAndRentNumber(equipType,number,allRentNum);
            if (flag == 1){
                equipmentRent.setRentRate(rentRates);
                return equipmentRentMapper.insEquipRent(equipmentRent);
            }
        }else return -1;
        log.info("更新器材数量失败！");
        return 0;
    }

    /**
     * 回收器材
     *      1、查询当前用户的租借某器材类型的器材数量
     *      2、把租借数量添加回器材可用数量上
     *      3、删除器材租借信息
     * @param id
     * @param eType 器材类型
     * @param userNumber 一卡通号
     * @return
     */
    @Override
    public Integer delEquipmentRent(Integer id, Integer eType, String userNumber) {
        // 租借后的器材数量
        Integer number = equipmentMapper.selEquipNumberByType(eType);
        // 用户租借的器材数量
        Integer rentNum = equipmentMapper.selEquipRentNumByUserNumAndType(id,eType, userNumber);
        // 修改器材数
        number += rentNum;
        // 全部器材租借数
        Integer allRentNum = 0;
        List<Integer> rentNumberList = equipmentMapper.selEquipRentNumByType(eType);
        for (Integer rentNumber : rentNumberList){
            allRentNum += rentNumber;
        }
        // 修改总得器材租借数
        allRentNum -= rentNum;
        // 更新器材数  器材租借数
        Integer flag = equipmentMapper.updEquipmentNumAndRentNumber(eType, number, allRentNum);
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
