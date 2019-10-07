package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {



// �������spring �Ѿ�ע���� ��������������   RedisConnectionFactory factory
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // ����key�����л���ʽ
        template.setKeySerializer(RedisSerializer.string());
        // ����value�����л���ʽ
        template.setValueSerializer(RedisSerializer.json());
        // ����hash��key�����л���ʽ
        template.setHashKeySerializer(RedisSerializer.string());
        // ����hash��value�����л���ʽ
        template.setHashValueSerializer(RedisSerializer.json());
        template.afterPropertiesSet();
        return template;
    }
}
