package com.example.jinniuprog.service.impl;

import com.example.jinniuprog.entity.User;
import com.example.jinniuprog.mapper.UserMapper;
import com.example.jinniuprog.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author luo
 * @since 2022-12-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
