package com.sms.contestmgmt.mapper;

import com.sms.contestmgmt.pojo.Contest;
import com.sms.contestmgmt.pojo.ContestEquip;
import com.sms.contestmgmt.pojo.Judgment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface ContestMapper {

    /**
     * 添加赛事
     * @param contest
     * @return
     */
    @Insert("INSERT INTO tb_sms_contest\n" +
            "VALUES\n" +
            "(default,#{name},#{type},#{placeId},#{timeId})")
    Integer addContest(Contest contest);

    /**
     * 级联更改场地状态，变为1
     * @param contest
     */
    @Update("UPDATE tb_sms_place\n" +
            "SET status = 1\n" +
            "WHERE id = #{placeId}")
    void updatePlaceStatus(Contest contest);


    /**
     * 添加裁判
     * @param judgment
     * @return
     */
    @Insert("INSERT INTO tb_sms_judgment\n" +
            "VALUES\n" +
            "(default,#{name},#{contestId})")
    Integer addJudge(Judgment judgment);

    /**
     * 通过赛事id查找裁判
     * @param id
     * @return
     */
    @Select("SELECT name FROM tb_sms_judgment WHERE contest_id = #{id}")
    ArrayList<String> findJudgeById(Integer id);

    /**
     * 删除裁判
     * @param id
     * @return
     */
    @Delete("DELETE FROM tb_sms_judgment WHERE id = #{id}")
    Integer delJudgeById(Integer id);

    /**
     * 为赛事添加器材
     * @param contestEquip
     * @return
     */
    @Insert("INSERT INTO tb_sms_contest_equip\n" +
            "VALUES\n" +
            "(default,#{contestId},#{equipId},#{equipNumber})")
    Integer addContestEquip(ContestEquip contestEquip);

    /**
     * 添加器材后级联器材表修改数量
     * @param contestEquip
     */
    @Update("UPDATE tb_sms_equipment \n" +
            "SET number=number-#{equipNumber}, rent_num=rent_num+#{equipNumber}\n" +
            "WHERE type = #{equipId}")
    void updataEquip(ContestEquip contestEquip);

    /**
     * 通过赛事id查询器材
     * @param id
     * @return
     */
    @Select("SELECT * FROM tb_sms_contest_equip WHERE contest_id = #{id}")
    ArrayList<ContestEquip> findEquipById(Integer id);

    /**
     * 通过赛事器材id删除记录
     * @param id
     * @return
     */
    @Delete("DELETE FROM tb_sms_contest_equip WHERE id = #{id}")
    Integer deleteContestEquipById(Integer id);

    /**
     * 查询全部赛事
     * @return
     */
//    @Select("SELECT * FROM tb_sms_contest")
    @Select("SELECT a.id,name,a.type,a.place_id as placeId,\n" +
            "(CASE c.type\n" +
            "\tWHEN 0 THEN '羽毛球场'\n" +
            "\tWHEN 1 THEN '乒乓球场'\t\n" +
            "\tWHEN 2 THEN '台球场'\n" +
            "\tWHEN 3 THEN '篮球场'\n" +
            "\tWHEN 4 THEN '保龄球场'\n" +
            "ELSE\n" +
            "\t'其他'\n" +
            "END) as placeType,\n" +
            "place_name as placeName,a.time_id,week,time_zone FROM tb_sms_contest a,tb_sms_time_slot b,tb_sms_place c\n" +
            "WHERE b.id = a.time_id AND a.place_id = c.id")
    ArrayList<Contest> findContest();

    /**
     * 通过id查询赛事
     * @param id
     * @return
     */
//    @Select("SELECT * FROM tb_sms_contest WHERE id = #{id}")
    @Select("SELECT a.id,name,a.type,a.place_id as placeId,\n" +
            "(CASE c.type\n" +
            "\tWHEN 0 THEN '羽毛球场'\n" +
            "\tWHEN 1 THEN '乒乓球场'\n" +
            "\tWHEN 2 THEN '台球场'\n" +
            "\tWHEN 3 THEN '篮球场'\n" +
            "\tWHEN 4 THEN '保龄球场'\n" +
            "ELSE\n" +
            "\t'其他'\n" +
            "END) as placeType,\n" +
            "place_name as placeName,a.time_id,week,time_zone FROM tb_sms_contest a,tb_sms_time_slot b,tb_sms_place c\n" +
            "WHERE b.id = a.time_id AND a.place_id = c.id AND a.id = #{id}")
    ArrayList<Contest> findContestById(Integer id);

    /**
     * 通过赛事类型查询赛事
     * @param type
     * @return
     */
//    @Select("SELECT * FROM tb_sms_contest WHERE type = #{type}")
    @Select("SELECT a.id,name,a.type,a.place_id as placeId,\n" +
            "(CASE c.type\n" +
            "\tWHEN 0 THEN '羽毛球场'\n" +
            "\tWHEN 1 THEN '乒乓球场'\t\n" +
            "\tWHEN 2 THEN '台球场'\n" +
            "\tWHEN 3 THEN '篮球场'\n" +
            "\tWHEN 4 THEN '保龄球场'\n" +
            "ELSE\n" +
            "\t'其他'\n" +
            "END) as placeType,\n" +
            "place_name as placeName,a.time_id,week,time_zone FROM tb_sms_contest a,tb_sms_time_slot b,tb_sms_place c\n" +
            "WHERE b.id = a.time_id AND a.place_id = c.id AND a.type = #{type}")
    ArrayList<Contest> findContestByType(Integer type);

    /**
     * 取消赛事
     * @param id
     * @return
     */
    @Delete("DELETE FROM tb_sms_contest WHERE id = #{id}")
    Integer deleteContestById(Integer id);
    /**
     * 级联删除赛事器材表
     * @param id
     */
    @Delete("DELETE FROM tb_sms_contest_equip WHERE contest_id = #{id}")
    void deleteContestEquipByContestId(Integer id);
    /**
     * 级联更新场地状态
     * @param id
     */
    @Update("UPDATE tb_sms_place\n" +
            "SET status = 0\n" +
            "WHERE id = (SELECT place_id FROM tb_sms_contest WHERE id = #{id})")
    void updatePlace(Integer id);

    /**
     * 查询所有赛事类型
     * @return
     */
    @Select("SELECT type\n" +
            "FROM tb_sms_contest\n" +
            "GROUP BY type")
    ArrayList<Integer> findContestType();

}
