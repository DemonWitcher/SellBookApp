package com.witcher.sellbook.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户实体类
 */
@Entity
public class User {

    /**
     * 用户id 主键 自增
     */
    @Id(autoincrement = true)
    private Long id;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 密码
     */
    private String password;
    /**
     * 我的地址
     */
    private String address;

    @Generated(hash = 1479042527)
    public User(Long id, String nickName, String phoneNumber, String password,
            String address) {
        this.id = id;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
