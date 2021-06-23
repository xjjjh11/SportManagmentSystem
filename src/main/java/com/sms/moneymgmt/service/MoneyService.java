package com.sms.moneymgmt.service;

import com.sms.announmgmt.pojo.Announ;
import com.sms.moneymgmt.pojo.Money;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface MoneyService {

    /**
     * 通过年份月份查找金额总额
     * @param year
     * @param month
     * @return
     */
    ArrayList<Money> findMoneyByYearMonth(Integer year, Integer month);

    /**
     * 通过年份月份生成柱状图
     * @param year
     * @param month
     * @return
     */
    List<Money> findBarByYearMonth(Integer year, Integer month);

    /**
     * 通过年份月份生成饼状图
     * @param year
     * @param month
     * @return
     */
    List<Money> findPieByYearMonth(Integer year, Integer month);
}
