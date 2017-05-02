package com.neworin.easynotes.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class LoginRecords {
    private Integer id;

    private String email;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}