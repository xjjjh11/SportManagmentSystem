package com.sms.moneymgmt.mapper;

import com.sms.moneymgmt.pojo.Money;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

public interface MoneyMapper {

    /**
     * 新增运营金额
     */
    @Insert("INSERT INTO tb_sms_operate_amount\n" +
            "VALUES\n" +
            "(DEFAULT, #{money}, #{year}, #{month}, #{day}, #{type}, #{remark})")
    Integer insEquipMoney(Money money);

    /**
     * 通过年份月份查找金额总额
     * @param year
     * @param month
     * @return
     */
    @Select("SELECT type,SUM(money) as sumYM\n" +
            "FROM tb_sms_operate_amount \n" +
            "WHERE year = #{year} AND month = #{month}\n" +
            "GROUP BY type")
    ArrayList<Money> findMoneyByYearMonth(Integer year, Integer month);

    /**
     * 通过年份月份生成柱状图
     * @param year
     * @param month
     * @return
     */
    @Select("SELECT day, SUM(CASE WHEN type = 0 THEN money ELSE 0 END) as daySumType0,\n" +
            "SUM(CASE WHEN type = 1 THEN money ELSE 0 END) as daySumType1,\n" +
            "SUM(CASE WHEN type = 2 THEN money ELSE 0 END) as daySumType2,\n" +
            "SUM(CASE WHEN type = 3 THEN money ELSE 0 END) as daySumType3\n" +
            "FROM tb_sms_operate_amount\n" +
            "WHERE year = #{year} AND month = #{month}\n" +
            "GROUP BY day")
    List<Money> findBarByYearMonth(Integer year, Integer month);

    /**
     * 通过年份月份生成饼状图
     * @param year
     * @param month
     * @return
     */
    @Select("SELECT \n" +
            "SUM(money) as value,\n" +
            "(\n" +
            "\tCASE type\n" +
            "\t\tWHEN 0 THEN '场地被使用收费' \n" +
            "\t\tWHEN 1 THEN '购置器材花费' \n" +
            "\t\tWHEN 2 THEN '出租器材收费'\n" +
            "\t\tWHEN 3 THEN '用户损毁器材的赔偿金'\n" +
            "\tELSE '其他'\n" +
            "END\n" +
            ") AS name\n" +
            "FROM tb_sms_operate_amount\n" +
            "WHERE year = #{year} and month = #{month}\n" +
            "GROUP BY \n" +
            "\tCASE type\n" +
            "\t\tWHEN 0 THEN '场地被使用收费' \n" +
            "\t\tWHEN 1 THEN '购置器材花费' \n" +
            "\t\tWHEN 2 THEN '出租器材收费'\n" +
            "\t\tWHEN 3 THEN '用户损毁器材的赔偿金'\n" +
            "\tELSE '其他'\n" +
            "\tEND\n" +
            "\t\n")
    List<Money> findPieByYearMonth(Integer year, Integer month);
}
