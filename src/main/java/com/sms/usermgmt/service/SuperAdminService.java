package com.sms.usermgmt.service;

/**
 * @author Jared
 * @date 2021/6/7 12:00
 */
public interface SuperAdminService {

    /**
     * 添加管理员（从已有用户中添加）
     * @param userNumber
     * @return
     */
    Integer updUserRole2Admin(String userNumber,int roleId);

    /**
     * 删除管理员（把管理员角色修改为普通用户）
     * @param userNumber
     * @return
     */
    Integer updAdminRole2User(String userNumber,int roleId);
}
