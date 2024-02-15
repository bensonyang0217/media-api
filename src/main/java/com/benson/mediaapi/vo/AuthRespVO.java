package com.benson.mediaapi.vo;

import java.io.Serializable;

public class AuthRespVO implements Serializable {
    private String token;

    public AuthRespVO(String token) {
        this.token = token;
    }

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
