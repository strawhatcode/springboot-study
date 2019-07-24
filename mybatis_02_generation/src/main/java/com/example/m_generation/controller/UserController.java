package com.example.m_generation.controller;

import com.example.m_generation.Bean.User;
import com.example.m_generation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 根据id删除一行
     * @param id
     * @return
     */
    @GetMapping("/del/{id}")
    public int delById(@PathVariable int id){
        return userService.deleteByPrimaryKey(id);
    }

    /**
     * 添加一行
     * @param name
     * @param age
     * @param gender
     * @param address
     * @return
     */
    @GetMapping("/insert")
    public User insert(String name,
                       int age,
                       String gender,
                       String address){
        User user1 = new User();
        user1.setName(name);
        user1.setAge(age);
        user1.setGender(gender);
        user1.setAddress(address);
        userService.insert(user1);
        return user1;
    }

    /**
     * 根据id查询一行数据
     * @param id
     * @return
     */
    @GetMapping("/selbyid/{id}")
    public User selectById(@PathVariable int id){
        return userService.selectByPrimaryKey(id);
    }

    /**
     * 查询所有数据
     * @return
     */
    @GetMapping("/selall")
    public List<User> selectall(){
        return userService.selectAll();
    }

}
