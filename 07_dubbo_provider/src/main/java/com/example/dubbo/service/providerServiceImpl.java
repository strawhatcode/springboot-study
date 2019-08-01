package com.example.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 *实现providerService服务，然后用【dubbo】的@Service注解
 * 然后consumer才可以用@reference注解获取到这个服务
 */
@Component  //把当前类交给spring管理
@Service    //dubbo的@service注解
public class providerServiceImpl implements providerService {
    @Override
    public String getstr(String string) {
        return "[provider]+++"+string;
    }
}

