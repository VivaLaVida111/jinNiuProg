package com.example.jinniuprog.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("CustomUserService")
@Component
public interface CustomUserService extends UserDetailsService {
    public UserDetails loadUserByUsername(String username);

}
