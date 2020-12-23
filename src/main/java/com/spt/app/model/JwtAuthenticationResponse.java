package com.spt.app.model;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private final String jwttoken;

    public JwtAuthenticationResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}