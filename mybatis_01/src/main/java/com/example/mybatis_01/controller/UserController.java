package com.example.mybatis_01.controller;

import com.example.mybatis_01.bean.User;
import com.example.mybatis_01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getall")
    public List<User> getall(){
        List<User> list = userService.selectAll();
        return list;
    }

    @GetMapping("/getbyid/{id}")
    public User getById(@PathVariable int id){
        User user = userService.selectById(id);
        return user;
    }
}
