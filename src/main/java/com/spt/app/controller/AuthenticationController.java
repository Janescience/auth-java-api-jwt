package com.spt.app.controller;

import com.spt.app.aop.annotation.Loggable;
import com.spt.app.model.AuthenticationRequest;
import com.spt.app.model.ResponseModel;
import com.spt.app.model.UserDTO;
import com.spt.app.respository.UserRepository;
import com.spt.app.service.AuthenticateService;
import com.spt.app.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticateService authenticateService;

    static final String TOKEN_PREFIX = "Bearer";

    @Loggable
    @PostMapping("/authenticate")
    public ResponseModel createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        Map userInfoMap = new HashMap();
        userInfoMap.put("access_token", TOKEN_PREFIX + " " + authenticateService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        userInfoMap.put("userInfo", userRepository.findByUsername(authenticationRequest.getUsername()));
        return new ResponseModel("Authenticated.", userInfoMap);
    }

    @Loggable
    @PostMapping("/register")
    public ResponseModel saveUser(@RequestBody UserDTO user){
        return new ResponseModel("Registered Successfully.", userService.register(user));
    }
}