package com.example.eureka_consumer.controller;

import com.example.eureka_consumer.service.helloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sayHelloController {
    private static Logger log = LoggerFactory.getLogger(sayHelloController.class);
    @Autowired
    helloService helloService;

    @GetMapping("/getconsumer")
    public String say(){
        log.info("这是服务消费者！！");
        return "[consumer端请求]-->"+helloService.sayhello();
    }
}
