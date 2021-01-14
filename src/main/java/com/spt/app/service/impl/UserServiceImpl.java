package com.spt.app.service.impl;

import com.spt.app.entity.app.MemberType;
import com.spt.app.entity.app.User;
import com.spt.app.model.UserDTO;
import com.spt.app.respository.MemberTypeRepository;
import com.spt.app.respository.UserRepository;
import com.spt.app.service.UserService;
import com.spt.app.util.AuthorizeUtil;
import com.spt.app.util.CustomException;
import com.spt.app.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.US);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private MemberTypeRepository memberTypeRepository;

    @Autowired
    private AuthorizeUtil authorizeUtil;


    @Override
    @Transactional
    public User register(UserDTO user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new CustomException("Information entered is incorrect.", "202", "Username has already been used.");
        }

        User newUser = modelMapper.map(user,User.class);
        newUser.setRefCode(this.generateRefCode(DateUtil.getCurrentDate(), user.getPhoneNumber()));
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));

        if (user.getSalary() < 15000) {
            throw new CustomException("Salary is lower than required.", "201", "Salary below 15000.");
        }

        Pattern digit = Pattern.compile("[0-9]{10}");
        if (!digit.matcher(user.getPhoneNumber()).find()) {
            throw new CustomException("Information entered is incorrect.", "202", "Phone number is 0-9 and 10 digit.");
        }


        String memberTypeCode;
        if (user.getSalary() >= 50000) {
            memberTypeCode = "01"; //Platinum
        } else if (user.getSalary() >= 30000) {
            memberTypeCode = "02"; //Gold
        } else{
            memberTypeCode = "03"; //Silver
        }

        MemberType memberType = memberTypeRepository.findByCode(memberTypeCode);
        newUser.setMemberType(memberType);

        userRepository.save(newUser);
        return newUser;

    }

    @Override
    public User getUser() {
        String username = authorizeUtil.getUsername();
        log.debug("User : {}", username);
        return userRepository.findByUsername(username);
    }

    private String generateRefCode(Date registerDate, String phoneNumber) {
        return DATE_FORMAT.format(registerDate) + phoneNumber.substring(phoneNumber.length() - 4);
    }

}
