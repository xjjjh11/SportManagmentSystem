package com.sms.moneymgmt.controller;

import com.sms.moneymgmt.pojo.Money;
import com.sms.moneymgmt.service.MoneyService;
import com.sms.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "运营金额操作接口")
@Slf4j
@RestController
@RequestMapping("/money")
public class MoneyController {

    @Autowired
    private MoneyService moneyService;

    @PostMapping("/queryMoneyByYearMonth")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year",value = "年份",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "month",value = "月份",dataType = "Integer",dataTypeClass = Integer.class)
    })
    @ApiOperation(value = "通过年月查询总额",notes = "通过年月查询总额")
    public Map<String, Object> showMoneyByYearMonth(@RequestParam("year") Integer year,@RequestParam("month") Integer month){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Money> allMoneys = moneyService.findMoneyByYearMonth(year,month);
        if (allMoneys != null){

            map.put("title","金额查询成功!");
            map.put("datas",allMoneys);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "金额查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryBarByYearMonth")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year",value = "年份",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "month",value = "月份",dataType = "Integer",dataTypeClass = Integer.class)
    })
    @ApiOperation(value = "柱状图查询",notes = "柱状图查询\"")
    public Map<String, Object> showBarByYearMonth(@RequestParam("year") Integer year,@RequestParam("month") Integer month){
        Map<String,Object> map = new HashMap<>();
        List<Money> allMoneys = moneyService.findBarByYearMonth(year,month);
        if (allMoneys != null){

            map.put("title","金额查询成功!");
            map.put("datas",allMoneys);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "金额查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryPieByYearMonth")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year",value = "年份",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "month",value = "月份",dataType = "Integer",dataTypeClass = Integer.class)
    })
    @ApiOperation(value = "饼状图查询",notes = "饼状图查询\"")
    public Map<String, Object> showPieByYearMonth(@RequestParam("year") Integer year,@RequestParam("month") Integer month){
        Map<String,Object> map = new HashMap<>();
        List<Money> allMoneys = moneyService.findPieByYearMonth(year,month);
        if (allMoneys != null){

            map.put("title","金额查询成功!");
            map.put("datas",allMoneys);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "金额查询失败!");
        return ResultUtil.resultError(map);
    }

}
