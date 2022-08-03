package com.witcher.sellbook.module;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;
    private String nickName;
    private String phoneNumber;
    private String password;
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
