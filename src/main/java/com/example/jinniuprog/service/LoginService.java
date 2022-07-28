package com.example.jinniuprog.service;

import com.example.jinniuprog.entity.User;
import org.springframework.stereotype.Service;

@Service("LoginService")
public interface LoginService {
    public String login(User user);
    public void loginSubSystem(User user);
}
