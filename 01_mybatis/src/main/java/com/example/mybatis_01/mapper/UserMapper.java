package com.example.mybatis_01.mapper;

import com.example.mybatis_01.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * DAO层
 * @Mapper注解 用来扫描mapper接口，如果使用@Mapper注解的话要在每个mapper接口中添加注解
 * @MapperScan注解 可以写在Mybatis01Application 类中的 @SpringBootApplication注解下面
 * 如：@MapperScan("com.example/mybatis_01/mybatis")
 */
//@Mapper
public interface UserMapper {
    //查询所有User的信息
    List<User> selectAll();

    //按照id查找User的信息
    User selectById(int id);
}
