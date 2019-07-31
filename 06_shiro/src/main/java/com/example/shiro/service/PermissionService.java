package com.example.shiro.service;

import com.example.shiro.bean.Permissions;

import java.util.List;

public interface PermissionService {
    List<Permissions> selectByRolesId(Integer id);

}
