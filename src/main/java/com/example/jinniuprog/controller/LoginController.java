package com.example.jinniuprog.controller;

import com.example.jinniuprog.entity.User;
import com.example.jinniuprog.service.LoginService;
import com.example.jinniuprog.service.ServiceImpl.LoginServiceImpl;
import com.example.jinniuprog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * 登录
     *
     * @param user 登录信息
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestBody User user) {
        return loginService.login(user);
    }
}
