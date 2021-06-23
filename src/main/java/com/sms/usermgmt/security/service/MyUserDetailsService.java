package com.sms.usermgmt.security.service;

import com.sms.usermgmt.mapper.MyUserDetailsMapper;
import com.sms.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * SpringSecurity用户的业务实现
 * @author Jared
 * @date 2021/6/3 15:32
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserDetailsMapper myUserDetailsMapper;

    /**
     * 查询用户信息
     * @param userNumber 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userNumber) throws UsernameNotFoundException {

        // 查询用户信息
        if (StringUtils.checkUserNumber(userNumber))
            return myUserDetailsMapper.selUserByUserNumber(userNumber);
        else return null;
    }
}
