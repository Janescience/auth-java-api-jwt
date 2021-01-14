package com.spt.app.service.impl;

import com.spt.app.security.JWTPrinciple;
import com.spt.app.service.AuthenticateService;
import com.spt.app.service.JwtUserDetailsService;
import com.spt.app.util.CustomException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticateService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTPrinciple JWTPrinciple;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    @Override
    public String authenticate(String username, String password) {
        this.authen(username, password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return JWTPrinciple.generateToken(userDetails);
    }

    @SneakyThrows
    private void authen(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new CustomException("USER_DISABLED", "202");
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid credentials.", "201","Username or password incorrect.");
        }
    }
}
