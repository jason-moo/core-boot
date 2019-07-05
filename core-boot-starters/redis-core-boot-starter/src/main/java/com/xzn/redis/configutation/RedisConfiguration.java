package com.xzn.redis.configutation;

import com.xzn.redis.RedisKeySerializer;
import com.xzn.redis.RedisValueSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfiguration {

    /**
     * RedisTemplate配置
     */
    @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setKeySerializer(new RedisKeySerializer());
        template.setValueSerializer(new RedisValueSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
