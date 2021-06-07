package com.sms.usermgmt.common;

/**
 * @author Jared
 * @date 2021/6/6 21:26
 */
public class RoleCode {
    /**
     * 普通用户
     */
    public final static Integer ROLE_USER = 1;
    /**
     * 管理员（包含普通用户权限）
     */
    public final static Integer ROLE_ADMIN = 2;
    /**
     * 超级管理员（包含管理员权限）
     */
    public final static Integer ROLE_SUPER_ADMIN = 3;
}
