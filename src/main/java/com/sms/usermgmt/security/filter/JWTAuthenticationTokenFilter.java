package com.sms.usermgmt.security.filter;


import com.sms.usermgmt.pojo.User;
import com.sms.util.JWTCommon;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jared
 * @date 2021/6/3 16:16
 */
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {



    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader("authorization");
        log.info("tokenHeader:"+tokenHeader);
        if (null!=tokenHeader && tokenHeader.startsWith(JWTCommon.TOKEN_PREFIX)) {
            try {
                // 截取JWT前缀
                String token = tokenHeader.replace(JWTCommon.TOKEN_PREFIX, "");
                // 解析JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(JWTCommon.JWT_SECRET)
                        .parseClaimsJws(token)
                        .getBody();
                log.info("claims:"+claims);
                // 获取用户名
                String username = claims.getSubject();
                String userId=claims.getId();
                if(!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(userId)) {
                    // 获取角色
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    String authority = claims.get(JWTCommon.JWT_CLAIMS_AUTHORITIES).toString();
                    if(!StringUtils.isEmpty(authority)) {
                        authorities =
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
                        log.info("authorities:" + authorities);
                    }
                        //组装参数
                        User user = new User();
                        user.setUsername(claims.getSubject());
                        user.setUserNumber(claims.getId());
                        user.setAuthorities(authorities);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, userId, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e){
                log.info("Token过期");
            } catch (Exception e) {
                log.info("Token无效");
            }
        }
        chain.doFilter(request, response);
    }
}
