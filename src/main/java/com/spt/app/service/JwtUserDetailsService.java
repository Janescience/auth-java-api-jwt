package com.spt.app.service;

import com.spt.app.respository.UserRepository;
import com.spt.app.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.spt.app.entity.app.User user = userDao.findByUsername(username);
        if (user == null) {
            throw new CustomException("User not found with username: " + username,"203");
        }
        return new User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

}