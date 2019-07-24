package com.example.mybatis_01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example/mybatis_01/mapper")
public class Mybatis01Application {

    public static void main(String[] args) {
        SpringApplication.run(Mybatis01Application.class, args);
    }

}
