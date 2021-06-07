package com.sms.usermgmt.service;

import com.sms.usermgmt.pojo.User;

import java.util.List;

/**
 * @author Jared
 * @date 2021/6/7 8:29
 */
public interface AdminService {

    /**
     * 查询全部用户信息（包括管理员和超级管理员）
     * @return
     */
    List<User> showAllUserInfo();

    /**
     * 查询全部普通用户信息
     * @return
     */
    List<User> showUserRoleForUser();

    /**
     * 查询全部管理员信息
     * @return
     */
    List<User> showUserRoleForAdmin();
}
