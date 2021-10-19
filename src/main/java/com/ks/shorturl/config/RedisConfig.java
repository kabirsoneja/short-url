package com.ks.shorturl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ks.shorturl.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {


    private static int redisPort = 18443;
//    @Value("${spring.redis.port}")
//    private static int redisPort;


    private static String redisHost = "redis-18443.c289.us-west-1-2.ec2.cloud.redislabs.com";
//    @Value("${spring.redis.host}")
//    private static String redisHost;


    private static String redisPassword = "IGO7IqkSRDKZyL7n5XxmxwAtHsM29jwO";
//    @Value("${spring.redis.password}")
//    private static String redisPassword;

    @Autowired
    ObjectMapper mapper;


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisHost);
        jedisConnectionFactory.setPort(redisPort);
        jedisConnectionFactory.setPassword(redisPassword);
        return jedisConnectionFactory;
    }



    @Bean
    public RedisTemplate<String, Url> redisTemplate() {

        RedisTemplate<String, Url> redisTemplate = new RedisTemplate<>();
        Jackson2JsonRedisSerializer valueSerializer = new Jackson2JsonRedisSerializer(Url.class);
        valueSerializer.setObjectMapper(mapper);
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(valueSerializer);
        return redisTemplate;
    }

    @Bean
    Jedis intializeJedisConnection(){
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisPassword);
        return jedis;

    }

}
