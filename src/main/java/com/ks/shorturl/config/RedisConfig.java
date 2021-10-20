package com.ks.shorturl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ks.shorturl.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {


    private static final int redisPort = 18443;

    private static final String redisHost = "redis-18443.c289.us-west-1-2.ec2.cloud.redislabs.com";

    private static final String redisPassword = "IGO7IqkSRDKZyL7n5XxmxwAtHsM29jwO";

    @Autowired
    ObjectMapper mapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        LOGGER.info("Initializing Jedis Connection Factory");
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisHost);
        jedisConnectionFactory.setPort(redisPort);
        jedisConnectionFactory.setPassword(redisPassword);
        return jedisConnectionFactory;
    }



    @Bean
    public RedisTemplate<String, Url> redisTemplate() {
        LOGGER.info("Initializing Redis Template");
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
        LOGGER.info("Initializing Jedis Connection");
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.auth(redisPassword);
        return jedis;

    }

}
