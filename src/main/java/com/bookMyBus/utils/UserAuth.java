package com.bookMyBus.utils;

import org.springframework.stereotype.Component;

@Component
public class UserAuth {

    private Integer userId;
    private String password;

    

    // Custom getter for userId
    public Integer getUserId() {
        return this.userId;
    }

    // Custom getter for password
    public String getPassword() {
        return this.password;
    }
}
