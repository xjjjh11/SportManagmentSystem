package com.sms.usermgmt.util;

import com.sms.usermgmt.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

/**
 * JWT工具类
 * @author Jared
 * @date 2021/6/3 11:24
 */
@Slf4j
public class JwtTokenUtil {

    /**
     * 私有化构造器
     */
    private JwtTokenUtil(){}

    /**
     * 生成token
     * @param user 用户信息
     * @return
     */
    public static String createAccessToken(User user){
        // 获取用户权限
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        StringBuffer sb = new StringBuffer();
        for (GrantedAuthority authority: authorities) {
            sb.append(authority).append(",");// 获取的角色用“，”隔开
        }
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 自定义属性 放入用户拥有权限
                .claim(JWTCommon.JWT_CLAIMS_AUTHORITIES, sb)
                // 放入用户账号
                .setId(user.getUserNumber())
                // 主题
                .setSubject(user.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer(JWTCommon.JWT_ISSUER)
                // 失效时间
                .setExpiration(new Date(JWTCommon.JWT_EXPIRATION))// 一小时过期
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JWTCommon.JWT_SECRET)
                // 生成
                .compact();
        return token;
    }
}
