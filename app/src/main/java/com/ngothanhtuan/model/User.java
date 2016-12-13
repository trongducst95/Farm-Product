package com.ngothanhtuan.model;

/**
 * Created by MyPC on 10/29/2016.
 */

public class User {
    String IP,Password;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User() {
    }

    public User(String IP, String password) {
        this.IP = IP;
        Password = password;
    }
}

