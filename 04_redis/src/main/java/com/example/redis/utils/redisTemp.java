package com.example.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * redis的工具类
 */
@Component
public class redisTemp {

    @Autowired
    @Qualifier("myredistemp")
    private RedisTemplate redisTemplate;

    /**
     * 指定key的值存在的时间，时间一到就会删除该数据
     * @param key   保存到redis时的key值
     * @param time  多少时间后删除数据
     * @return
     */
    public boolean expire(String key,long time){
        try {
            if (time > 0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
