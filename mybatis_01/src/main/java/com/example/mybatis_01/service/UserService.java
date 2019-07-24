package com.example.mybatis_01.service;

import com.example.mybatis_01.bean.User;

import java.util.List;

/**
 * Service层
 * 主要写接口
 */
public interface UserService {
    List<User> selectAll();
    User selectById(int id);
}
