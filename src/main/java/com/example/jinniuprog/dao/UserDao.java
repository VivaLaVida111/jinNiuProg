package com.example.jinniuprog.dao;

import com.example.jinniuprog.entity.Role;
import com.example.jinniuprog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface UserDao {
    public User findByUserName(String username);
    public int add(User user);
    public List<User> queryAll();
    public String getUserById(int id);
    void updatePassword(User user);
    List<Role> getUserRolesByUserId(Integer Id);
}
