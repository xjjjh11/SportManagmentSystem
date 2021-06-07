package com.sms.usermgmt.mapper;

import com.sms.usermgmt.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/7 8:32
 */
public interface AdminMapper {

    /**
     * 查询全部用户信息
     * @return
     */
    @Select("SELECT * FROM tb_sms_user")
    List<User> selAllUserInfo();

    /**
     *根据userNumber查询该用户的角色
     * @param userNumber 一卡通号或教职工号
     * @return
     */
    @Select("SELECT role_code\n" +
            "FROM tb_sms_role r\n" +
            "LEFT JOIN tb_sms_user_role ur ON r.id = ur.role_id\n" +
            "LEFT JOIN tb_sms_user u ON u.user_number = ur.user_number\n" +
            "WHERE u.user_number = #{userNumber}")
    List<String> findRoleByUserNum(@Param("userNumber") String userNumber);
}
