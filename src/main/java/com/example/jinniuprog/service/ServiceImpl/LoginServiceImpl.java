package com.example.jinniuprog.service.ServiceImpl;

import com.example.jinniuprog.dao.UserDao;
import com.example.jinniuprog.entity.User;
import com.example.jinniuprog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDao userdao;
    @Override
    public String login(User user) {
        if (user == null || user.getName().isEmpty()) {
            return "用户名不能为空!";
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                return "用户名不存在或密码错误";
            } else {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        //user.setRoles(userdao.getUserRolesByUserId(user.getId()));
        //System.out.println(user.getRoles());
        return "登录成功";
    }

    @Override
    public void loginSubSystem(User user) {

    }
}

