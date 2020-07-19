package com.xiaozhi.mp.entity;

import java.util.Date;

/**
 *
 *
 * @author zhangzy
 * @date 2020/7/19-16:29
 * @since v1.0
 */
/**
    * 系统用户
    */
public class SysUser {
    private Long userId;

    /**
    * 用户名
    */
    private String username;

    /**
    * 密码
    */
    private String password;

    /**
    * 盐
    */
    private String salt;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 手机号
    */
    private String mobile;

    /**
    * 状态  0：禁用   1：正常
    */
    private Byte status;

    /**
    * 部门ID
    */
    private Long deptId;

    /**
    * 创建时间
    */
    private Date createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}