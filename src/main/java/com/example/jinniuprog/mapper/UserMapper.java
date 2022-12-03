package com.example.jinniuprog.mapper;

import com.example.jinniuprog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author luo
 * @since 2022-12-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
