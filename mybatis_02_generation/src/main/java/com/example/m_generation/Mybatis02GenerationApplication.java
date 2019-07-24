package com.example.m_generation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.m_generation.mapper")
@SpringBootApplication
public class Mybatis02GenerationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Mybatis02GenerationApplication.class, args);
    }

}
