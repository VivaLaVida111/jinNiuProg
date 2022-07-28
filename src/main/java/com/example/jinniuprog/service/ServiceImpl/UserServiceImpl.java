package com.example.jinniuprog.service.ServiceImpl;

import com.example.jinniuprog.dao.UserDao;
import com.example.jinniuprog.entity.Msg;
import com.example.jinniuprog.entity.User;
import com.example.jinniuprog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;
@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public List<User> queryAll(){
        return userdao.queryAll();
    }

    @Override
    public String getUserById(int id) {
        return userdao.getUserById(id);
    }

    @Override
    public String updatePassword(User user) {
        if (user==null||user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
            return "用户名或密码为空";
        }
        //加密
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //更新密码
        userdao.updatePassword(user);
        return "更新密码成功";

    }

    @Override
    public void updateMsg(Msg msg) {
        updateMsg(msg);
    }

    ;
    public User findByUserName(String username){
        return userdao.findByUserName(username);
    };
    public int add(User user){
        return userdao.add(user);
    };

}
