package com.sms.contestmgmt.controller;


import com.sms.contestmgmt.pojo.Contest;
import com.sms.contestmgmt.pojo.ContestEquip;
import com.sms.contestmgmt.pojo.Judgment;
import com.sms.contestmgmt.service.ContestService;
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
import java.util.Map;

@Api(tags = "赛事操作接口")
@Slf4j
@RestController
@RequestMapping("/contest")
public class ContestController {

    @Autowired
    ContestService contestService;

    @PostMapping("/admin/addContest")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "赛事名称",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "type",value = "赛事类型",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "placeId",value = "赛事场地id",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "timeId",value = "赛事时间段id",dataType = "Integer",dataTypeClass = Integer.class)
    })
    @ApiOperation(value = "添加赛事",notes = "管理员添加赛事")
    public Map<String, Object> addContest(@RequestParam("name") String name, @RequestParam("type") Integer type,
                                          @RequestParam("placeId") Integer placeId, @RequestParam("timeId") Integer timeId){
        Map<String,Object> map = new HashMap<>();
        Contest contest = new Contest();
        contest.setName(name);
        contest.setType(type);
        contest.setPlaceId(placeId);
        contest.setTimeId(timeId);
        if (contestService.addContest(contest) == 1){
            map.put("title","成功添加赛事!");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "添加赛事失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryContestType")
    @ApiOperation(value = "查询所有赛事类型",notes = "查询所有赛事类型")
    public Map<String, Object> showContestType(){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Integer> allContests = contestService.findContestType();
        if (allContests != null){
            map.put("title","所有赛事类型查询成功!");
            map.put("datas",allContests);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "所有赛事类型查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/admin/addJudge")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "裁判姓名",dataType = "String",dataTypeClass = String.class),
            @ApiImplicitParam(name = "contestId",value = "裁判参与赛事id",dataType = "Integer",dataTypeClass = Integer.class)
    })
    @ApiOperation(value = "添加裁判",notes = "管理员添加裁判")
    public Map<String, Object> addJudge(@RequestParam("name") String name,@RequestParam("contestId") Integer contestId){
        Map<String,Object> map = new HashMap<>();
        Judgment judgment = new Judgment();
        judgment.setName(name);
        judgment.setContestId(contestId);
        if (contestService.addJudge(judgment) == 1){
            map.put("title","成功添加裁判!");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "添加裁判失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryJudgeById")
    @ApiImplicitParam(name = "id",value = "赛事id",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "通过赛事id查询裁判",notes = "通过赛事id查询裁判")
    public Map<String, Object> showJudgeById(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        ArrayList<String> allJudgments = contestService.findJudgeById(id);
        if (allJudgments != null){
            map.put("title","裁判查询成功!");
            map.put("datas",allJudgments);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "裁判查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/admin/deleteAnnounce")
    @ApiImplicitParam(name = "id",value = "裁判id",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "删除裁判",notes = "删除裁判")
    public Map<String, Object> deleteJudge(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        if (contestService.delJudgeById(id) == 1){
            map.put("title","成功删除裁判!");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "删除裁判失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/admin/addContestEquip")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contestId",value = "使用该器材赛事id",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "equipId",value = "器材id",dataType = "Integer",dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "equipNumber",value = "器材数量",dataType = "Integer",dataTypeClass = Integer.class)
    })
    @ApiOperation(value = "添加赛事器材",notes = "管理员添加赛事")
    public Map<String, Object> addContestEquip(@RequestParam("contestId")Integer contestId,@RequestParam("equipId")Integer equipId,
                                               @RequestParam("equipNumber")Integer equipNumber){
        Map<String,Object> map = new HashMap<>();
        ContestEquip contestEquip = new ContestEquip();
        contestEquip.setContestId(contestId);
        contestEquip.setEquipId(equipId);
        contestEquip.setEquipNumber(equipNumber);
        if (contestService.addContestEquip(contestEquip) == 1){
            map.put("title","成功添加赛事器材!");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "添加赛事器材失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryEquipById")
    @ApiImplicitParam(name = "id",value = "赛事id",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "通过赛事id查询器材",notes = "通过赛事id查询器材")
    public Map<String, Object> showEquipById(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        ArrayList<ContestEquip> allContestEquips = contestService.findEquipById(id);
        if (allContestEquips != null){
            map.put("title","器材查询成功!");
            map.put("datas",allContestEquips);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "器材查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/admin/deleteContestEquipById")
    @ApiImplicitParam(name = "id",value = "赛事器材记录id",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "删除赛事器材记录",notes = "删除赛事器材记录")
    public Map<String, Object> deleteContestEquipById(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        if (contestService.deleteContestEquipById(id) == 1){
            map.put("title","成功删除赛事器材记录!");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "删除赛事器材记录失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/admin/deleteContest")
    @ApiImplicitParam(name = "id",value = "赛事id",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "取消赛事",notes = "取消赛事")
    public Map<String, Object> deleteContestById(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        if (contestService.deleteContestById(id) == 1){
            map.put("title","成功取消赛事!");
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "取消赛事失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryContest")
    @ApiOperation(value = "查询所有赛事",notes = "查询所有赛事")
    public Map<String, Object> showContest(){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Contest> allContests = contestService.findContest();
        if (allContests != null){
            map.put("title","所有赛事查询成功!");
            map.put("datas",allContests);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "所有赛事查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryContestById")
    @ApiImplicitParam(name = "id",value = "赛事id",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "通过赛事id查询赛事",notes = "通过赛事id查询赛事")
    public Map<String, Object> showContestById(@RequestParam("id") Integer id){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Contest> allContests = contestService.findContestById(id);
        if (allContests != null){
            map.put("title","赛事查询成功!");
            map.put("datas",allContests);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "赛事查询失败!");
        return ResultUtil.resultError(map);
    }

    @PostMapping("/queryContestByType")
    @ApiImplicitParam(name = "type",value = "赛事体育类型",dataType = "Integer",dataTypeClass = Integer.class)
    @ApiOperation(value = "通过赛事类型查询赛事",notes = "通过赛事类型查询赛事")
    public Map<String, Object> showContestByType(@RequestParam("type") Integer type){
        Map<String,Object> map = new HashMap<>();
        ArrayList<Contest> allContests = contestService.findContestByType(type);
        if (allContests != null){
            map.put("title","赛事查询成功!");
            map.put("datas",allContests);
            return ResultUtil.resultSuccess(map);
        }
        map.put("title", "赛事查询失败!");
        return ResultUtil.resultError(map);
    }

}
