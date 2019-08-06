package com.example.eureka_consumer.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//value:根据服务名称获取对应的服务
//fallback:当服务断开时，用自定义的字符串当结果返回，实现当前接口的类，然后返回自定义的内容
@FeignClient(value = "eureka-provider", fallback = helloServiceImpl.class)
public interface helloService {

    //获取/get请求，引入进来之后就可以使用了
    //这个方法就是跟provider服务中的controller方法一样
    @GetMapping("/get")
    String sayhello();
}
