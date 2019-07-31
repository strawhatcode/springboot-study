package com.example.shiro.service;

import com.example.shiro.bean.Roles;
import com.example.shiro.mapper.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    RolesMapper rolesMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return rolesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Roles record) {
        return rolesMapper.insert(record);
    }

    @Override
    public Roles selectByUserName(String name) {
        return rolesMapper.selectByUserName(name);
    }

    @Override
    public List<Roles> selectAll() {
        return rolesMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Roles record) {
        return rolesMapper.updateByPrimaryKey(record);
    }
}
