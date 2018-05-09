package com.infiniteskills.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class UserImpl implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "PASSWORD")
    private String password;


    public void setEmailAddress(String newEmail) { this.emailAddress = newEmail; }


    public String getEmailAddress() {
        return this.emailAddress;
    }


    public void setPassword(String newPassword) {
        this.password = newPassword;
    }


    public String getPassword() { return this.password; }


    public void setUserName(String newName) { this.userName = newName; }


    public String getUserName() {
        return this.userName;
    }


    public void setUserId(long id) {
        this.userId = id;
    }

    public long getUserId() {
        return this.userId;
    }
}
