package com.example.dubbo;

import com.example.dubbo.service.consumerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    consumerService consumerService;
    @Test
    public void contextLoads() {
        consumerService.getByProvider();
    }

}
