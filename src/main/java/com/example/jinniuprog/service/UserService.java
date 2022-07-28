package com.example.jinniuprog.service;

import com.example.jinniuprog.entity.Msg;
import com.example.jinniuprog.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("UserService")
public interface UserService {
    public User findByUserName(String username);
    public int add(User user);
    public List<User> queryAll();
    public String getUserById(int id);
    public String updatePassword(User user);
    public void updateMsg(Msg msg);

//    private void encryptPassword(User user) {
//        String password = user.getPassword();
//        password = new BCryptPasswordEncoder().encode(password);
//        user.setPassword(password);
//
//    }
}
