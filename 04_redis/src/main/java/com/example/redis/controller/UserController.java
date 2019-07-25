package com.example.redis.controller;

import com.example.redis.bean.User;
import com.example.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("myredistemp") //Qualifier注解表示指定注入bean的名称，spring根据value值查找名称为value值的bean注解
    private RedisTemplate template;

//    @Autowired
//    redisTemp redistemp;


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

    @GetMapping("/selbyid/{id}")
    public User selectById(@PathVariable int id){
        User user = userService.selectByPrimaryKey(id);

        //把user存进redis缓存中，并指定key为user.getName()
        template.opsForValue().set(user.getName(),user);
        System.out.println("从redis里Get回来的数据"+template.opsForValue().get(user.getName()));
        return user;
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
