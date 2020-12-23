package com.spt.app.controller;

import com.spt.app.config.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin()
public class    GreetingController {

    @Autowired
    private JwtAuthenticationToken jwtAuthenticationToken;

    @GetMapping("/greeting")
    public String getEmployees(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");

        String username = null;

        if (requestTokenHeader != null) {
            String jwtToken = requestTokenHeader.substring(7);
            username = jwtAuthenticationToken.getUsernameFromToken(jwtToken);
        }

        return "Welcome " + username + " :D";
    }

}
