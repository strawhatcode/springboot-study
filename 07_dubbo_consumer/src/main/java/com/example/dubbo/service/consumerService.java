package com.example.dubbo.service;


import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component   //把这个类交给spring管理，才可以使用@Autowired注解引入这个类
public class consumerService {

    //引用provider注册过的服务
    @Reference
    providerService test;

    /**
     * 自定义一个方法来处理provider注册过的服务
     * @return
     */
    public String getByProvider(){
        String s = test.getstr("88888888888888");
        return "consumer收到"+s;
    }

}
