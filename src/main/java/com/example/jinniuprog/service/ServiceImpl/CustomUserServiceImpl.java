package com.example.jinniuprog.service.ServiceImpl;

import com.example.jinniuprog.dao.UserDao;
import com.example.jinniuprog.entity.Role;
import com.example.jinniuprog.entity.User;
import com.example.jinniuprog.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service("CustomUserService")
@Component
public class CustomUserServiceImpl implements CustomUserService  {
    @Autowired UserDao userdao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userdao.findByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role :user.getRoles()){
            authorities.add((new SimpleGrantedAuthority(role.getName())));
            System.out.println(role.getName());
        }

         return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), authorities);

    }
}
