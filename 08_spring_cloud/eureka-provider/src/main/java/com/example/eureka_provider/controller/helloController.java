package com.example.eureka_provider.controller;

import com.example.eureka_provider.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这是自己定义的一个服务
 */
@RestController
public class helloController {
    @Value("${server.port}")
    String port;

    @Autowired
    HelloService helloService;

    @GetMapping("/get")
    public String hello(){
        return "来自端口号为["+port+"]provider端返回的"+helloService.hello("[provider内容]");
    }
}
