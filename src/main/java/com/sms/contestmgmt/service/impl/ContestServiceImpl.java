package com.sms.contestmgmt.service.impl;

import com.sms.contestmgmt.mapper.ContestMapper;
import com.sms.contestmgmt.pojo.Contest;
import com.sms.contestmgmt.pojo.ContestEquip;
import com.sms.contestmgmt.pojo.Judgment;
import com.sms.contestmgmt.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Override
    public Integer addContest(Contest contest) {
        contestMapper.updatePlaceStatus(contest);
        return contestMapper.addContest(contest);
    }

    @Override
    public Integer addJudge(Judgment judgment) {
        return contestMapper.addJudge(judgment);
    }

    @Override
    public ArrayList<String> findJudgeById(Integer id) {
        return contestMapper.findJudgeById(id);
    }

    @Override
    public Integer delJudgeById(Integer id) {
        return contestMapper.delJudgeById(id);
    }

    @Override
    public Integer addContestEquip(ContestEquip contestEquip) {
        contestMapper.updataEquip(contestEquip);
        return contestMapper.addContestEquip(contestEquip);
    }

    @Override
    public ArrayList<ContestEquip> findEquipById(Integer id) {
        return contestMapper.findEquipById(id);
    }

    @Override
    public Integer deleteContestEquipById(Integer id) {
        return contestMapper.deleteContestEquipById(id);
    }

    @Override
    public Integer deleteContestById(Integer id) {
        contestMapper.updatePlace(id);
        contestMapper.deleteContestEquipByContestId(id);
        return contestMapper.deleteContestById(id);
    }

    @Override
    public ArrayList<Contest> findContest() {
        return contestMapper.findContest();
    }

    @Override
    public ArrayList<Contest> findContestById(Integer id) {
        return contestMapper.findContestById(id);
    }

    @Override
    public ArrayList<Contest> findContestByType(Integer type) {
        return contestMapper.findContestByType(type);
    }

    @Override
    public ArrayList<Integer> findContestType() {
        return contestMapper.findContestType();
    }


}
