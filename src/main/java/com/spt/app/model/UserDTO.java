package com.spt.app.model;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private Double salary;
    private String phoneNumber;
    private String address;
}
