package com.spt.app.model;

import lombok.Data;

@Data
public class ResponseModel {
    private String message;
    private String code;
    private Object data;
    private String status;

    public ResponseModel(){

    }

    public ResponseModel(String message, Object data){
        this.message=message;
        this.data=data;
        this.code="200";
        this.status="success";
    }
}
