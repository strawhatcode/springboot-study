package com.example.shiro.mapper;

import com.example.shiro.bean.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByUserName(String user_name);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}