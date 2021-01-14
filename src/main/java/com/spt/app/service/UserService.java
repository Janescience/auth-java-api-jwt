package com.spt.app.service;

import com.spt.app.entity.app.User;
import com.spt.app.model.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {
    User register(UserDTO user);
    User getUser();
}