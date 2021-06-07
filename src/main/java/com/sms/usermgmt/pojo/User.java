package com.sms.usermgmt.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Jared
 * @date 2021/6/2 14:28
 */
@ApiModel("用户类")
@Data
public class User implements UserDetails {
    /**
     * 学生一卡通号或教职工的职工号(唯一标识性)
     */
    @ApiModelProperty("用户账号")
    private String userNumber;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     *密码
     */
    @ApiModelProperty("密码")
    private String password;
    /**
     *性别（0：女  1：男）
     */
    @ApiModelProperty("性别")
    private Integer gender;
    /**
     *手机号
     */
    @ApiModelProperty("手机号")
    private String phone;
    /**
     *学院
     */
    @ApiModelProperty("学院")
    private String academy;
    /**
     *专业
     */
    @ApiModelProperty("专业")
    private String major;
    /**
     *班级
     */
    @ApiModelProperty("班级")
    private String classes;
    /**
     *账号是否可用（0：可用  1：禁用）
     *  @Getter(value = AccessLevel.NONE)：去除掉get方法
     *      与下面的isEnabled()方法冲突了，导致映射数据库中的enabled字段失败
     */
    @Getter(value = AccessLevel.NONE)
    @ApiModelProperty("账号是否可用；0：可用 1：禁用")
    private Integer enabled;
    /**
     *账号注册时间
     */
    @ApiModelProperty("用户注册时间")
    private String createTime;
    /**
     *账号所拥有的权限
     */
    @ApiModelProperty("用户权限")
    private List<GrantedAuthority> authorities;

/**----------------    实现UserDetails中的方法   -----------------------------*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
