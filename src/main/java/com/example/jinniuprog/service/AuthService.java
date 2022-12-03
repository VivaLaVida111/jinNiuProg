package com.example.jinniuprog.service;

import com.example.jinniuprog.entity.User;

public interface AuthService {
    /**
     * 根据用户名获取用户信息
     */
    User getUserByName(String name);

    /**
     * 注册
     */
    User register(User userParam);

    /**
     * 登录
     * @return 生成的JWT的token
     */
    String login(String username, String password);

}
