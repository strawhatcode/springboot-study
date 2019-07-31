package com.example.shiro.mapper;

import com.example.shiro.bean.Roles;
import java.util.List;

public interface RolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Roles record);

    Roles selectByUserName(String name);

    List<Roles> selectAll();

    int updateByPrimaryKey(Roles record);
}