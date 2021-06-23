package com.sms.util;

/**
 * TOKEN常量
 * @author Jared
 * @date 2021/6/4 11:14
 */
public class JWTCommon {

    /**
     * token签发者
     */
    public final static String JWT_ISSUER = "Jared";
    /**
     * token默认过期时间（7天）
     */
    public final static Long JWT_EXPIRATION = System.currentTimeMillis() + 7*24*60*60*1000;
    /**
     * token签名（盐）
     */
    public final static String JWT_SECRET = "JWT_SECRET";
    /**
     * 自定义的KEY(权限)
     */
    public final static String JWT_CLAIMS_AUTHORITIES = "authorities";
    /**
     * 自己添加的token头部
     */
    public final static String TOKEN_PREFIX = "Jared-";
}
