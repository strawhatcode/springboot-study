package com.example.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class hiService {

    //把RestTemplate注入进来
    @Autowired
    RestTemplate restTemplate;

    //断路器的注解，fallbackMethod参数是实现断路后自定义的类
    @HystrixCommand(fallbackMethod = "sayHiServiceFallBack")
    public String sayHiService(){
        //【restTemplate】的[getForObject]方法是get请求，第一个参数是get请求的路径，
        // 这里把【localhost:port】用注册的【provier名称】代替，
        //第二个参数是返回值的类型
        String temp = restTemplate.getForObject("http://EUREKA-PROVIDER/get",String.class);
        System.out.println(temp);
        return temp;
    }

    /**
     * 这个是断路时自定义的一个类做返回
     * @return
     */
    public String sayHiServiceFallBack(){
        return "[ribbon消费者]：生产者服务没有开启或不存在，返回自定义字符串";
    }


}
