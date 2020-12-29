package com.spt.app.security;

import java.io.Serializable;

public class JWTAuthenticationToken implements Serializable {

    private final String jwttoken;

    public JWTAuthenticationToken(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}