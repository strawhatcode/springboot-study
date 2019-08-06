package com.example.eureka_consumer.service;

import org.springframework.stereotype.Component;

@Component
public class helloServiceImpl implements helloService {
    @Override
    public String sayhello() {
        return "服务断开，这是hystrix自定义返回的一串字符串";
    }
}
