package com.example.shiro.mapper;

import com.example.shiro.bean.Permissions;
import java.util.List;

public interface PermissionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permissions record);

    List<Permissions> selectByRolesId(Integer id);

    List<Permissions> selectAll();

    int updateByPrimaryKey(Permissions record);
}