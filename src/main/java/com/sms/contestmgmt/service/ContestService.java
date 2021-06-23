package com.sms.contestmgmt.service;

import com.sms.contestmgmt.pojo.Contest;
import com.sms.contestmgmt.pojo.ContestEquip;
import com.sms.contestmgmt.pojo.Judgment;
import io.swagger.models.auth.In;

import java.util.ArrayList;

public interface ContestService{

    /**
     * 添加赛事
     * @param contest
     * @return
     */
    Integer addContest(Contest contest);


    /**
     * 添加裁判
     * @param judgment
     * @return
     */
    Integer addJudge(Judgment judgment);

    /**
     * 通过赛事id查找裁判
     * @param id
     * @return
     */
    ArrayList<String> findJudgeById(Integer id);

    /**
     * 删除裁判
     * @param id
     * @return
     */
    Integer delJudgeById(Integer id);

    /**
     * 为赛事配置器材
     * @param contestEquip
     * @return
     */
    Integer addContestEquip(ContestEquip contestEquip);

    /**
     * 通过赛事id查询器材
     * @param id
     * @return
     */
    ArrayList<ContestEquip> findEquipById(Integer id);

    /**
     * 通过赛事器材id删除赛事记录
     * @param id
     * @return
     */
    Integer deleteContestEquipById(Integer id);

    /**
     * 取消赛事
     * @param id
     * @return
     */
    Integer deleteContestById(Integer id);

    /**
     * 查询所有赛事
     * @return
     */
    ArrayList<Contest> findContest();

    /**
     * 通过id查询赛事
     * @param id
     * @return
     */
    ArrayList<Contest> findContestById(Integer id);

    /**
     * 通过赛事类型查询id
     * @param type
     * @return
     */
    ArrayList<Contest> findContestByType(Integer type);


    /**
     * 查询所有赛事类型
     * @return
     */
    ArrayList<Integer> findContestType();
}
