package com.spt.app.model;

import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable {

    private String username;
    private String password;

    //need default constructor for JSON Parsing
    public JwtAuthenticationRequest() {

    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}