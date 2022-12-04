package com.example.jinniuprog.controller;

import com.example.jinniuprog.dto.UserLoginParam;
import com.example.jinniuprog.entity.User;
import com.example.jinniuprog.service.AuthService;
//import com.example.jinniuprog.service.InfoService;
import com.example.jinniuprog.service.InfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("//auth")
public class AuthController {
    @Resource
    private AuthService authService;
    @Resource
    private InfoService infoService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping("/register")
    public Boolean register(@RequestBody User userParam) {
        String username = userParam.getName();
        String password = userParam.getPassword();
        if (username == null || password == null) {
            return false;
        }
        User user = authService.register(userParam);
        if (user == null) {
            return false;
        }
        return true;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserLoginParam userLoginParam) {
        String username = userLoginParam.getName();
        String password = userLoginParam.getPassword();
        Map<String, String> tokenMap = new HashMap<>();
        if (username == null || password == null) {
            tokenMap.put("error_message", "username or password is null");
            return tokenMap;
        }
        String token = authService.login(username, password);
        if (token == null) {
            tokenMap.put("error_message", "username or password is not correct");
            return tokenMap;
        }
        //tokenMap = infoService.getInfo();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return tokenMap;
    }

    @PostMapping("/change_password")
    public Boolean changePassword(@RequestBody String password) {
        if (password == null) {
            return false;
        }
        Map<String, String> infoMap = infoService.getInfo();
        String name = infoMap.get("name");
        return authService.changePassword(name, password);
    }
}
