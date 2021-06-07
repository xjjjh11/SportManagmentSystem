package com.sms.usermgmt.security;

import com.sms.usermgmt.mapper.AdminMapper;
import com.sms.usermgmt.mapper.MyUserDetailsMapper;
import com.sms.usermgmt.pojo.User;
import com.sms.usermgmt.security.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的登录验证类
 *      用户进入登录页，输入账号信息后进入此方法
 * @author Jared
 * @date 2021/6/3 15:30
 */
@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 这里不能写Mapper类，先做测试用，到时候写service层
     */
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 验证用户的账号信息
     *      1.用户名是否存在
     *      2.密码是否正确
     *      3.账号是否被禁用
     * 验证成功后获取用户权限，并返回全部用户信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 获取表单输入中返回的用户账号
        String userNumber = (String) authentication.getPrincipal();
        log.info("userNumber:"+userNumber);
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询用户是否存在
        User userInfo = (User) myUserDetailsService.loadUserByUsername(userNumber);
        log.info("userInfo:"+userInfo);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, userInfo.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (!userInfo.isEnabled()){
            throw new LockedException("该用户已被禁用");
        }
        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 查询用户角色
        List<String> rolesCodes = adminMapper.findRoleByUserNum(userNumber);
        for (String roleCode: rolesCodes){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleCode));
        }
        userInfo.setAuthorities(authorities);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
        log.info("authenticationToken:"+authenticationToken);
        // 进行验证登录登录
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
