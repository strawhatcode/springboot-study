package com.example.dubbo.controller;

import com.example.dubbo.service.consumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testcontroller {

    @Autowired
    consumerService consumerService;

    @GetMapping("get")
    public String get(){
        return consumerService.getByProvider();
    }

}
