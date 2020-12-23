package com.javainuse.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javainuse.model.User;
import com.javainuse.respository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JwtAuthenticationUserDetailsService implements UserDetailsService {

    protected Gson gson = new GsonBuilder().create();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public Map save(String user) {

        String message, code;

        JSONObject userJsonObj = new JSONObject(user);
        String username = userJsonObj.optString("username", null);
        String password = bcryptEncoder.encode(userJsonObj.optString("password", null));
        String address = userJsonObj.optString("address", null);
        String phone = userJsonObj.optString("phone", null);
        Double salary = userJsonObj.optDouble("salary", 0d);

        User userDup = userRepository.findByUsername(username);

        if (userDup != null) {
            message = "Username is already";
            code = "409";//Conflict
        } else {
            if (salary > 15000) {
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
                String currentDate = dateFormat.format(new Date());

                String last4Digit = phone.substring(6, 10);
                String refCode = currentDate + last4Digit;

                String memberType = null;
                if (salary >= 50000) {
                    memberType = "Platinum";
                } else if (salary >= 30000) {
                    memberType = "Gold";
                } else if (salary < 30000) {
                    memberType = "Silver";
                }

                User newUser = new User();
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setAddress(address);
                newUser.setPhone(phone);
                newUser.setSalary(salary);
                newUser.setRefCode(refCode);
                newUser.setMemberType(memberType);
                userRepository.save(newUser);

                Map userInfoMap = new HashMap();
                userInfoMap.put("userInfo", newUser);

                Map dataMap = new HashMap();
                dataMap.put("message", "Registered Successfully");
                dataMap.put("code", "201");//Created
                dataMap.put("data", userInfoMap);

                Map resultMap = new HashMap();
                resultMap.put("success", dataMap);

                return resultMap;
            } else {
                message = "Rejected because salary < 15000";
                code = "403";
            }
        }

        Map dataMap = new HashMap();
        dataMap.put("message", message);
        dataMap.put("code", code);

        Map resultMap = new HashMap();
        resultMap.put("error", dataMap);

        return resultMap;
    }
}