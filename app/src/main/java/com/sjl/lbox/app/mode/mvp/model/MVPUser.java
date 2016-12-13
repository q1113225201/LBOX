package com.sjl.lbox.app.mode.mvp.model;

import java.io.Serializable;

/**
 * Created by SJL on 2016/12/13.
 */

public class MVPUser implements Serializable {
    private String username;

    private String password;

    public MVPUser() {

    }

    public MVPUser(String username, String password) {
        this.username = username;
        this.password = password;
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
}
