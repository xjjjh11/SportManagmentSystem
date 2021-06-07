package com.sms.usermgmt.service.impl;

import com.sms.usermgmt.mapper.AdminMapper;
import com.sms.usermgmt.mapper.MyUserDetailsMapper;
import com.sms.usermgmt.pojo.User;
import com.sms.usermgmt.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jared
 * @date 2021/6/7 8:29
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    /**
     * 全部用户信息
     */
    List<User> users = new ArrayList<>();

    /**
     * 查询全部用户信息和对应的角色信息
     * @return
     */
    public List<User> showAllUserInfo(){
        users = adminMapper.selAllUserInfo();
        if (users != null){
            for (User user : users){
                // 角色集合
                List<GrantedAuthority> authorities = new ArrayList<>();
                List<String> rolesCodes = adminMapper.findRoleByUserNum(user.getUserNumber());
                for (String roleCode: rolesCodes){
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + roleCode));
                }
                user.setAuthorities(authorities);
            }
        return users;
        }else return null;
    }

    /**
     * 查询全部普通用户信息
     * @return
     */
    @Override
    public List<User> showUserRoleForUser() {
        // 1、查询全部用户信息
        // 2、筛选出管理员和超级管理员
        // 3、其余user存入集合中
        users = adminMapper.selAllUserInfo();
        if (users != null) {
            Iterator<User> iterUsers = users.iterator();
            while (iterUsers.hasNext()){
                User user = iterUsers.next();
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                List<String> roleCodes = adminMapper.findRoleByUserNum(user.getUserNumber());
                Iterator<String> iterRoles = roleCodes.iterator();
                while(iterRoles.hasNext()){
                    String role = iterRoles.next();
                    // 删除角色为 管理员或超级管理员 的用户
                    if (role.equals("admin") || role.equals("superAdmin")){
                        iterUsers.remove();
                        role = "";
                    }
                    if (!role.equals(""))
                        authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
                }
                user.setAuthorities(authorities);
            }
            return users;
        }else return null;
    }

    /**
     * 查询全部管理员用户信息
     * @return
     */
    @Override
    public List<User> showUserRoleForAdmin() {
        // 1、查询全部用户信息
        // 2、筛选出普通用户
        // 3、其余user存入集合中
        users = adminMapper.selAllUserInfo();
        if (users != null) {
            Iterator<User> iterUsers = users.iterator();
            while (iterUsers.hasNext()){
                User user = iterUsers.next();
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                List<String> roleCodes = adminMapper.findRoleByUserNum(user.getUserNumber());
                Iterator<String> iterRoles = roleCodes.iterator();
                while(iterRoles.hasNext()){
                    String role = iterRoles.next();
                    // 删除角色为 user 的用户
                    if (role.equals("user")){
                        iterUsers.remove();
                        role = "";
                    }
                    if (!role.equals(""))
                        authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
                }
                user.setAuthorities(authorities);
            }
            return users;
        }else return null;

    }
}
