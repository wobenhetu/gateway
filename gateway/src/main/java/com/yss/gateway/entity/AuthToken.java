package com.yss.gateway.entity;

import javax.security.auth.Subject;
import java.security.Principal;

public class AuthToken implements Principal {

    private String token;

    private String username;

    private String password;

    public void setToken(String token) {
        this.token = token;
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


    @Override
    public String getName() {
        return token;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
