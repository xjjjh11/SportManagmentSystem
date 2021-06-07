package com.sms.usermgmt.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author Jared
 * @date 2021/6/7 11:40
 */
public interface SuperAdminMapper {

    /**
     *
     * @param userNumber
     * @param roleId
     * @return
     */
    @Update("UPDATE tb_sms_user_role " +
            "SET role_id = #{roleId}\n" +
            "WHERE user_number = #{userNumber}")
    Integer updUserRole(@Param("userNumber") String userNumber,
                              @Param("roleId") Integer roleId);
}
