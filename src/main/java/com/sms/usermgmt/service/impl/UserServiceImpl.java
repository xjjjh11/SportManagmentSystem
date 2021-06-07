package com.sms.usermgmt.service.impl;

import com.sms.usermgmt.common.RoleCode;
import com.sms.usermgmt.mapper.AdminMapper;
import com.sms.usermgmt.mapper.MyUserDetailsMapper;
import com.sms.usermgmt.pojo.User;
import com.sms.usermgmt.service.UserService;
import com.sms.usermgmt.util.ResultUtil;
import com.sms.usermgmt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jared
 * @date 2021/6/6 16:40
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MyUserDetailsMapper myUserDetailsMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户注册账号
     * @param user 用户信息
     * @return
     */
    @Override
    public Integer insUser(User user) {
        String userNumber = user.getUserNumber();
        String username = user.getUsername();
        String password = user.getPassword();
        String phone = user.getPhone();
        // 查询数据库中的全部用户
        List<String> userNumbers = myUserDetailsMapper.selAllUserNumbers();
        for(String number : userNumbers){
            // 该用户已存在
            if (number.equals(userNumber))
                return ResultUtil.USER_EXISTED;
        }
        // 用户必填信息不能为空
        if (StringUtils.isEmpty(userNumber) && StringUtils.isEmpty(username) &&
            StringUtils.isEmpty(password) && StringUtils.isEmpty(phone))
            return ResultUtil.USER_INFO_NULL;
        // 正则表达式匹配用户账号、密码和手机号
        else if (!StringUtils.checkUserNumber(userNumber))
            return ResultUtil.USER_NUMBER_ERROR;
        else if (!StringUtils.checkPassword(password))
            return ResultUtil.PASSWORD_ERROR;
        else if (!StringUtils.checkPhoneNumber(phone))
            return ResultUtil.PHONE_NUMBER_ERROR;
        else {
            // 使用强哈希算法加密密码
            String encodePwd = passwordEncoder.encode(password);
            user.setPassword(encodePwd);
            // 用户注册成功后，默认其角色为 user
            if (myUserDetailsMapper.insUser(user) == 1){
                myUserDetailsMapper.insRoleByUserNum(userNumber, RoleCode.ROLE_USER);
                return 1;
            }else
                return 0;
        }
    }

    /**
     * 查询用户个人信息
     * @param userNumber
     * @return
     */
    @Override
    public User selUserByUserNumber(String userNumber) {
        User user = myUserDetailsMapper.selUserByUserNumber(userNumber);
        List<String> roles = adminMapper.findRoleByUserNum(userNumber);
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        user.setAuthorities(authorities);
        return user;
    }

    /**
     * 修改密码
     * @param userNumber 一卡通号
     * @param newPassword 新密码
     * @return
     */
    @Override
    public Integer updPwdByUserNumber(String userNumber ,String newPassword) {
        if (userNumber != null){
            // 正则检测新输入的密码
            if (StringUtils.checkPassword(newPassword)) {
                // 加密密码
                String encode = passwordEncoder.encode(newPassword);
                System.out.println("encode:"+encode);
                return myUserDetailsMapper.updPwdByUserNumber(userNumber,encode);
            } else return ResultUtil.PASSWORD_ERROR;
        }else return ResultUtil.USER_NUMBER_ERROR;
    }

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
    @Override
    public Integer updUserInfoByUserNumber(String userNumber, Integer gender, String academy, String major, String classes, String phone) {
        if (userNumber == null)
            return ResultUtil.USER_NUMBER_ERROR;
        else if (!StringUtils.checkPhoneNumber(phone))
            return ResultUtil.PHONE_NUMBER_ERROR;
        return myUserDetailsMapper.updUserInfoByUserNumber(userNumber,gender,academy,major,classes,phone);
    }
}
