package com.example.rabbitmq.controller;


import com.example.rabbitmq.bean.User;
import com.example.rabbitmq.sender.SimpleQueueSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {

    @Autowired
    SimpleQueueSend simpleQueueSend;

    User user;

    @GetMapping("/simple")
    public void simpleQueue(){
        for (int i=0;i<10;i++) {
            simpleQueueSend.send();
        }
    }

    @GetMapping("direct")
    public User directExchangeQueue(){
        user = new User();
        user.setName("李四");
        user.setAge(22);
        user.setGender("男");
        simpleQueueSend.directSend(user);
        User user1 = new User();
        user1.setName("王五");
        user1.setAge(23);
        simpleQueueSend.directSend2(user1);
        return user;
    }


    @GetMapping("topic")
    public String topicExchangeQueue(){
        User user1 = new User();
        user1.setName("小小");
        user1.setAddress("广东");
        User user2 = new User();
        user2.setName("中中");
        user2.setGender("男");
        User user3 = new User();
        user3.setName("大大");
        user3.setAge(23);
        simpleQueueSend.topicSend(user1);
        simpleQueueSend.topicSend2(user2);
        simpleQueueSend.topicSend3(user3);
        User user4 = new User();
        user4.setName("特特");
        user4.setPhone("132545456546");
        simpleQueueSend.topicSend4(user4);
        return "发送";
    }


    @GetMapping("fanout")
    public String fanoutExchangeQueue(){
        user = new User();
        user.setName("翻翻");
        user.setAge(24);
        simpleQueueSend.fanoutSend(user);
        return "fanout";
    }
}
