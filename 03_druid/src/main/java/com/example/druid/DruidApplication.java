package com.example.druid;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.druid.mapper")
@SpringBootApplication
public class DruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(DruidApplication.class, args);
    }

}
