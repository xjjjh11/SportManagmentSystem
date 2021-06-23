package com.sms.usermgmt.service;

import com.sms.usermgmt.pojo.User;

/**
 * @author Jared
 * @date 2021/6/6 16:38
 */
public interface UserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    Integer insUser(User user);

    /**
     * 查询用户个人信息
     * @return
     */
    User selUserByUserNumber(String userNumber);

    /**
     * 修改密码
     * @param userNumber
     * @return
     */
    Integer updPwdByUserNumber(String userNumber,String newPassword);

    /**
     * 修改用户信息
     * @param userNumber 一卡通号
     * @param gender 性别
     * @param academy 学院
     * @param major 专业
     * @param classes 班级
     * @param phone 手机号
     * @return
     */
    Integer updUserInfoByUserNumber(String userNumber,String username,Integer gender,String academy,
                                    String major,String classes,String phone);

    /**
     * 查询用户密码
     * @param userNumber
     * @return
     */
    String findPwdByUserNumber(String userNumber);
}
