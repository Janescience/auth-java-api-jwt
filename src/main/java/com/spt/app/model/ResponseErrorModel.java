package com.spt.app.model;

import lombok.Data;

@Data
public class ResponseErrorModel {
    private String message;
    private String code;
    private String status;
    private String info;
}
