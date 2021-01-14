package com.spt.app.controller;

import com.spt.app.model.AuthenticationRequest;
import com.spt.app.model.ResponseModel;
import com.spt.app.model.UserDTO;
import com.spt.app.respository.UserRepository;
import com.spt.app.security.JWTPrinciple;
import com.spt.app.service.JwtUserDetailsService;
import com.spt.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTPrinciple JWTPrinciple;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    static final String TOKEN_PREFIX = "Bearer";


    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = JWTPrinciple.generateToken(userDetails);

        Map userInfoMap = new HashMap();
        userInfoMap.put("access_token",  TOKEN_PREFIX + " " +token);
        userInfoMap.put("userInfo", userRepository.findByUsername(authenticationRequest.getUsername()));

        return new ResponseModel("Authenticated.", userInfoMap);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel saveUser(@RequestBody UserDTO user) throws Exception {
        return new ResponseModel("Registered Successfully.", userService.register(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}