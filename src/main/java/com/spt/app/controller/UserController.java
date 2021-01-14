package com.spt.app.controller;

import com.spt.app.aop.annotation.Loggable;
import com.spt.app.model.ResponseModel;
import com.spt.app.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Loggable
    @GetMapping("/user")
    public ResponseModel getUser() {
        return new ResponseModel("Success.", userService.getUser());
    }

}
