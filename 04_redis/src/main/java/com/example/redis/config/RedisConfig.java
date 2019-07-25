package com.example.redis.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.UnknownHostException;


//把当前配置类注入到spring容器中
@Configuration
public class RedisConfig {

    @Bean("myredistemp") //把当前方法注入到spring中
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //RedisTemplate<K, V>中的key值使用StringRedisSerializer序列化方式
        template.setKeySerializer(RedisSerializer.string());
        //RedisTemplate<K, V>中的value值使用jackson序列化方式
        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        //Hash中的key值使用StringRedisSerializer序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        //Hash中的value值使用jackson序列化方式
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        template.afterPropertiesSet();
        return template;
    }
}
