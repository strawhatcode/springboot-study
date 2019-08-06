package com.example.eureka_consumer.controller;

import com.example.eureka_consumer.service.helloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sayHelloController {

    @Autowired
    helloService helloService;

    @GetMapping("/getconsumer")
    public String say(){
        return "[consumer端请求]-->"+helloService.sayhello();
    }
}
