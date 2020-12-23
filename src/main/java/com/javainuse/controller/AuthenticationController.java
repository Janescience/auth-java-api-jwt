package com.javainuse.controller;

import com.javainuse.config.JwtAuthenticationToken;
import com.javainuse.model.JwtAuthenticationRequest;
import com.javainuse.respository.UserRepository;
import com.javainuse.service.JwtAuthenticationUserDetailsService;
import flexjson.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private JwtAuthenticationToken jwtAuthenticationToken;

    @Autowired
    private JwtAuthenticationUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtAuthenticationToken.generateToken(userDetails);

        Map userInfoMap = new HashMap();
        userInfoMap.put("token", token);
        userInfoMap.put("userInfo", userRepository.findByUsername(authenticationRequest.getUsername()));

        Map dataMap = new HashMap();
        dataMap.put("data", userInfoMap);
        dataMap.put("message", "Authenticated");
        dataMap.put("code", "200");

        Map resultMap = new HashMap();
        resultMap.put("success", dataMap);

        return new JSONSerializer()
                .prettyPrint(true)
                .exclude("*.class")
                .exclude("*.password")
                .deepSerialize(resultMap);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveUser(@RequestBody String user) throws Exception {
        return new JSONSerializer()
                .prettyPrint(true)
                .exclude("*.class")
                .exclude("*.password")
                .deepSerialize(userDetailsService.save(user));
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