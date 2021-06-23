package com.sms.usermgmt.mapper;

import com.sms.usermgmt.pojo.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/2 14:36
 */
@Mapper
public interface MyUserDetailsMapper {

    /**
     * 用户注册
     * @param user 用户注册信息
     * @return
     */
    @Insert("INSERT INTO tb_sms_user\n" +
            " VALUES\n" +
            "(#{userNumber},#{username},#{password},#{gender},#{academy}" +
            ",#{major},#{classes},#{phone},#{enabled},#{createTime})")
    Integer insUser(User user);

    /**
     * 查询全部用户的一卡通号
     * @return
     */
    @Select("SELECT user_number FROM tb_sms_user")
    List<String> selAllUserNumbers();

    /**
     * 注册用户角色
     * @param userNumber
     * @param roleId
     * @return
     */
    @Insert("INSERT INTO tb_sms_user_role\n" +
            "VALUES\n" +
            "(default,#{userNumber},#{roleId})")
    Integer insRoleByUserNum(String userNumber,int roleId);


    /**
     * 根据userNumber查询用户信息
     * @param userNumber 一卡通号或教职工号
     * @return
     */
    @Select("SELECT user_number,username,password,gender,phone," +
                "academy,major,classes,enabled,create_time\n" +
            "FROM tb_sms_user\n" +
            "WHERE user_number = #{userNumber}")
    User selUserByUserNumber(String userNumber);

    /**
     * 根据userNumber查询用户密码
     * @param userNumber 一卡通号或教职工号
     * @return
     */
    @Select("SELECT password FROM tb_sms_user\n" +
            "WHERE user_number = #{userNumber}")
    String selPwdByUserNumber(String userNumber);

    /**
     * 修改密码
     * @param userNumber 一卡通号
     * @param newPwd 新密码
     * @return
     */
    @Update("UPDATE tb_sms_user " +
            "SET `password`= #{password}\n" +
            "WHERE user_number = #{userNumber}")
    Integer updPwdByUserNumber(String userNumber,@Param("password") String newPwd);

    /**
     * 用户修改个人信息
     * @param userNumber 一卡通号
     * @param gender 性别
     * @param academy 学院
     * @param major 专业
     * @param classes 班级
     * @param phone 手机号
     * @return
     */
    @Update("UPDATE tb_sms_user " +
            "SET username = #{username},gender= #{gender},academy= #{academy},major= #{major}," +
                            "classes= #{classes},phone= #{phone}\n" +
            "WHERE user_number = #{userNumber}")
    Integer updUserInfoByUserNumber(String userNumber,String username,Integer gender, String academy,
                                    String major, String classes, String phone);

}
