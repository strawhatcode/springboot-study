package com.example.redis.service;

import com.example.redis.bean.User;

import java.util.List;


public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

}
