package com.example.shiro.service;

import com.example.shiro.bean.User;

import java.util.List;

public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByUserName(String user_name);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}
