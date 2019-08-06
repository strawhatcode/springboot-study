package com.example.ribbon.controller;

import com.example.ribbon.service.hiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hiController {
    @Autowired
    hiService hiservice;

    @GetMapping("/getribbon")
    public String sayhi(){
        return "这是ribbon发出的请求【"+hiservice.sayHiService();
    }
}
