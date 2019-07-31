package com.example.shiro.service;

import com.example.shiro.bean.Permissions;
import com.example.shiro.mapper.PermissionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionsMapper permissionsMapper;

    @Override
    public List<Permissions> selectByRolesId(Integer id) {
        return permissionsMapper.selectByRolesId(id);
    }
}
