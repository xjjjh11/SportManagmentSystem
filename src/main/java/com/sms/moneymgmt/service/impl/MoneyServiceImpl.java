package com.sms.moneymgmt.service.impl;

import com.sms.moneymgmt.mapper.MoneyMapper;
import com.sms.moneymgmt.pojo.Money;
import com.sms.moneymgmt.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoneyServiceImpl implements MoneyService {

    @Autowired
    private MoneyMapper moneyMapper;

    @Override
    public ArrayList<Money> findMoneyByYearMonth(Integer year, Integer month) {
        return moneyMapper.findMoneyByYearMonth(year, month);
    }

    @Override
    public List<Money> findBarByYearMonth(Integer year, Integer month) {
        return moneyMapper.findBarByYearMonth(year, month);
    }

    @Override
    public List<Money> findPieByYearMonth(Integer year, Integer month) {
        return moneyMapper.findPieByYearMonth(year, month);
    }
}
