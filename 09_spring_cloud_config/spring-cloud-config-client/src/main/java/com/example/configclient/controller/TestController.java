package com.example.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.ProtectionDomain;

@RestController
@RefreshScope          //刷新注解，使用http://127.0.0.1:8202/actuator/bus-refresh可以刷新
public class TestController {
    @Value("${test}") //引用配置文件中的test属性
    String test;
    @Value("${server.port}")
    String port;

    @GetMapping("/gettest")
    public String gettest(){
        return "端口号【"+ port +"】"+test;
    }
}
