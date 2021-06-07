package com.sms.usermgmt.service.impl;

import com.sms.usermgmt.mapper.SuperAdminMapper;
import com.sms.usermgmt.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jared
 * @date 2021/6/7 12:00
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    @Autowired
    private SuperAdminMapper superAdminMapper;

    /**
     * 把已有用户变为管理员
     * @param userNumber 用户账号
     * @param roleId 角色码
     * @return
     */
    @Override
    public Integer updUserRole2Admin(String userNumber,int roleId) {

        return superAdminMapper.updUserRole(userNumber,roleId);
    }

    /**
     * 删除管理员，即把管理员角色变为普通用户
     * @param userNumber 用户账号
     * @param roleId 角色码
     * @return
     */
    @Override
    public Integer updAdminRole2User(String userNumber, int roleId) {
        return superAdminMapper.updUserRole(userNumber,roleId);
    }
}
