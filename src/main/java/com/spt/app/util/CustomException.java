package com.spt.app.util;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private String message;
    private String code;
    private String status;
    private String info;

    public CustomException(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, String code, String info) {
        this.message = message;
        this.code = code;
        this.info = info;
    }
}
